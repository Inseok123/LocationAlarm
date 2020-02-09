#include <M5Stack.h>
#include <SimpleTimer.h>

String sms = "AT*SMSMO=01097499745,,1,1,B1E4B1DEBBF3C8B2C0D4B4CFB4D92E\r\n";
String ch;
String gps = "11";
int a = 0;
char c;    // \n 인지 구분 및 str에 저장.
String str = "";  // \n 전까지 c값을 저장
String targetStr = "GPS";   // str의 값이 NMEA의 GPS 값인지 타겟
int i = 0;
SimpleTimer timer;

String first1;
String two1;
String three1;
String four1;
String thirteen1;
String fourteen1;
String fifteen1;
String sixteen1;
String x;     // 위도 경도
String y;
int two1i;

void setup() {

  M5.begin();

  Serial.begin(115200);

  // Serial2.begin(unsigned long baud, uint32_t config, int8_t rxPin, int8_t txPin, bool invert)
  Serial2.begin(115200, SERIAL_8N1, 16, 17);

  Display_LCD();      // 기본 LCD 화면 설정

}

void loop() {
  timer.run();
  M5.update();


  PressButton();      // 버튼 눌렀을 때 긴급 문자 발송
  GetGPS();           // GPS 수신
  SendDB();           // GPS 정보 DB로 전송
  
 
  
  if (Serial.available()) {
    ch = Serial.readString();
    Serial2.print(ch);
  }

  if (Serial2.available()) {
    //  ch = Serial2.readString();

    //   Serial.write(ch);
    //   Serial.print(ch);

    c = Serial2.read();
    //   Serial.print(c);
    if ( c == '\n') {

      // \n 일시. 지금까지 저장된 str 값이 targetStr과 맞는지 구분
      if (targetStr.equals(str.substring(2, 5))) {

        // ,를 토큰으로서 파싱



        int zero     = str.indexOf(",");
        int first    = str.indexOf(",", zero + 1);                   // 날짜(UTC) 연월일
        int two      = str.indexOf(",", first + 1);                  // 시간(UTC) 시분초
        int three    = str.indexOf(",", two + 1);                    // 위도
        int four     = str.indexOf(",", three + 1);                  // 경도
        int five     = str.indexOf(",", four + 1);                   // 속도
        int six      = str.indexOf(",", five + 1);                   // 방향
        int seven  = str.indexOf(",", six + 1);                    // 측위 성공 여부( V : 성공, A : 실패)
        int eight    = str.indexOf(",", seven + 1);                  // LTE 통신 상태 ( 2 : 성공, 그 외 : 실패)
        int nine     = str.indexOf(",", eight + 1);                  // EMM Reject (LTE 연결 실패 원인 정보)
        int ten      = str.indexOf(",", nine + 1);                   // ESM Reject (LTE 연결 실패 원인 정보)
        int eleven   = str.indexOf(",", ten + 1);                    // LTE 신호 세기
        int twelve   = str.indexOf(",", eleven + 1);                 // 위성 개수
        int thirteen = str.indexOf(",", twelve + 1);              // 위성 ID - 신호세기
        int fourteen = str.indexOf(",", thirteen + 1);            // 위성 ID - 신호세기
        int fifteen = str.indexOf(",", fourteen + 1);             // 위성 ID - 신호세기
        int sixteen = str.indexOf(",", fifteen + 1);              // 위성 ID - 신호세기


        first1 = str.substring(zero + 1, first);
        two1 = str.substring(first + 1, two);
        three1 = str.substring(two + 1, three);
        four1 = str.substring(three + 1, four);
        String five1 = str.substring(four + 1, five);
        String six1 = str.substring(five + 1, six);
        String seven1 = str.substring(six + 1, seven);
        String eight1 = str.substring(seven + 1, eight);
        String nine1 = str.substring(eight + 1, nine);
        String ten1 = str.substring(nine + 1, ten);
        String eleven1 = str.substring(ten + 1, eleven);
        String twelve1 = str.substring(eleven + 1, twelve);
        thirteen1 = str.substring(twelve + 1, thirteen);
        fourteen1 = str.substring(thirteen + 1, fourteen);
        fifteen1 = str.substring(fourteen + 1, fifteen);
        sixteen1 = str.substring(fifteen + 1, sixteen);



        String Lat = str.substring(two + 1, three);
        String Long = str.substring(three + 1, four);

        String Lat1 = Lat.substring(0, 2);
        String Lat2 = Lat.substring(2);
        // Long의 앞값과 뒷값을 구분

        String Long1 = Long.substring(0, 3);
        String Long2 = Long.substring(3);

        // 좌표 계산.

        double LatF = Lat1.toDouble() + Lat2.toDouble() / 100000;
        float LongF = Long1.toFloat() + Long2.toFloat() / 100000;

        x = String(LatF, 15);
        y = String(LongF, 15);

        two1i = two1.toInt() + 90000;
        // 좌표 출력.

        Serial.print("날짜 : ");  Serial.println(first1);
        Serial.print("시간 : ");  Serial.println(two1i);
        Serial.print("위도 : ");  Serial.println(LatF, 15);
        Serial.print("경도 : ");  Serial.println(LongF, 15);
        /*
                Serial.print("속도 : ");  Serial.println(five1);
                Serial.print("방향 : ");  Serial.println(six1);
                Serial.print("측위 성공 여부 : ");  Serial.println(seven1);
                Serial.print("LTE 통신 상태 : ");  Serial.println(eight1);
                Serial.print("EMM Reject : ");  Serial.println(nine1);
                Serial.print("ESM Reject : ");  Serial.println(ten1);
                Serial.print("LTE 신호 세기 : ");  Serial.println(eleven1);
                Serial.print("위성 개수 : ");  Serial.println(twelve1);
                Serial.print("위성ID - 신호세기 : ");  Serial.println(thirteen1);
                Serial.print("위성ID - 신호세기 : ");  Serial.println(fourteen1);
                Serial.print("위성ID - 신호세기 : ");  Serial.println(fifteen1);
                Serial.print("위성ID - 신호세기 : ");  Serial.println(sixteen1);


        */

      }

      // str 값 초기화

      str = "";


    } else {    // \n 아닐시, str에 문자를 계속 더하기
      str += c;
    }
  }

}


void Display_LCD() {

  M5.Lcd.setBrightness(100);
  M5.Lcd.clear(BLACK);
  M5.Lcd.setTextSize(4);
  M5.Lcd.setCursor(60, 10);

}

void PressButton() {
  if (M5.BtnA.wasReleasefor(3000)) {
    M5.Lcd.setTextColor(RED);
    M5.Lcd.print("Emergency");
    Serial2.println(sms);
  }
  else if (M5.BtnC.wasPressed()) {
    M5.Lcd.clear(BLACK);
    M5.Lcd.setCursor(0, 0);
  }
}

void GetGPS() {
  if (i == 0)
  {
    delay(100);
    Serial2.println("AT$$GPS\r\n");
    Serial.println("gps load");
    
    delay(100);
    Serial2.println("AT+WSOCR=0,52.79.189.85,9999,2,0\r\n");
    delay(100);
    //Serial2.println("AT$$GPSSTOP\r\n");
    //delay(100);
    //    Serial2.println("AT+WSOCO=0\r\n");
    //    delay(10);
    i++;
  }
}

void SendDB() {
   if ( i == 1)
  {
    timer.setTimeout(15000, SendData);
  }
}



void SendData() {
  String message;
  message = first1 + "," + two1i + "," + x + "," + y;
  Serial.println(message);

  Serial2.print("AT+WSOWR=0,");
  Serial2.print(message.length());
  Serial2.print(",");
  Serial2.print(message);
  Serial2.println("\r\n");
}
