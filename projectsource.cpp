#include <deprecated.h>
#include <MFRC522.h>
#include <MFRC522Extended.h>
#include <require_cpp11.h>
#include <SPI.h>

//Arduino - zamek elektroniczny
//Urządzenie: ARDUINO MEGA
//Autorzy Adam Krizar - 241276, Katarzyna Czajkowska - XXXXXX

//Bibliotrki
#include <Keypad.h>
#include <LiquidCrystal_I2C.h>
#include <Password.h>
#include <SoftwareSerial.h>

//klawiatura
const int KOL = 4;
const int WIER = 4;

//Ustawianie obsługi rfid
MFRC522 rfio(53, 5);
MFRC522::MIFARE_Key ak2; 

int card[4];

LiquidCrystal_I2C lcd(0x3F,16, 2); //To kod podłączenia wyświetlacza.

char keys[WIER][KOL] = 
{
    {'1', '2', '3', 'A'},
    {'4', '5', '6', 'B'},
    {'7', '8', '9', 'C'},
    {'*', '0', '#', 'D'}
};

byte wierp[WIER] = {A0, A1, A2, A3};
byte kolp[KOL] = {A4,A5,A6,A7};
Keypad keypad = Keypad (makeKeymap(keys), wierp, kolp, WIER, KOL);

//hasło
Password password = Password("1234");
char newpass[20];
char temp[20];
char pass[20];
int count = 0;
bool change = false;

// Główna pętla programu
void loop()
{
    keypad.getKey();
    if(rfio.PICC_IsNewCardPresent())
    {
      digitalWrite(10, LOW);
      if(ReadRFIO()) rfioopen();
      else rfioclose();
      digitalWrite(10, HIGH);
      cls();
    }
    delay(10);
}

//zczytywanie i sprawdzanie kodu karty
bool ReadRFIO()
{
  rfio.PICC_ReadCardSerial();
  bool temp = true;
  for(int i=0;i<rfio.uid.size;i++)
  {
    if(rfio.uid.uidByte[i]!=card[i]) temp = false;
  }
  rfio.PICC_HaltA();
  rfio.PCD_StopCrypto1();
  count = 0;
  password.reset();
  return temp;
}

void rfioopen()
{
  digitalWrite(8, HIGH);
  lcd.clear();
  lcd.print("OPEN");
  delay(5000);
  digitalWrite(8, LOW);
}

void rfioclose()
{
  digitalWrite(9, HIGH);
  lcd.clear();
  lcd.print("WRONG");
  delay(2000);
  digitalWrite(9, LOW);
}

//Startowe ustawianie zamka
void setup()
{
    pinMode(8, OUTPUT);
    pinMode(9, OUTPUT);
    pinMode(10, OUTPUT);
    Serial.begin(9600);
    SPI.begin();
    rfio.PCD_Init();
    lcd.init();
    lcd.backlight();
    lcd.home();
    cls();
    lcd.setCursor(0,1);
    keypad.addEventListener(keypadEvent); //TU I NAZWE FUNKCJI PONIZEJ MOZE TRZEBA ZMIENIC NA keypadEvent
    digitalWrite(10, HIGH);
    card[0] = 80;
    card[1] = 37;
    card[2] = 17;
    card[3] = 7;
}

//potwierdzenie wpisywanego hasła
void confirmpass()
{
  digitalWrite(10, LOW);
    char clean;
    for(int i=0; i<count; i++)
    {
      password.append(pass[i]);
    }
    if(password.evaluate())
    {
      digitalWrite(8, HIGH);
        lcd.clear();
        lcd.print("OPEN");
        delay(5000);
        digitalWrite(8, LOW);
    }
    else
    {
      digitalWrite(9, HIGH);
        lcd.clear();
        lcd.print("WRONG");
        delay(2000);
        digitalWrite(9, LOW);
    }
    password.reset();
    cls();
    for(int i = 0; i<20; i++)
    {
      pass[i] = clean;
    }
    count = 0;
    digitalWrite(10, HIGH);
}

bool enterpass()
{
    char clean;
    for(int i=0; i<count; i++)
    {
      password.append(pass[i]);
    }
    for(int i = 0; i<20; i++)
    {
      pass[i] = clean;
    }
    count = 0;
    return password.evaluate();
}

//potwierdzenie zmiany hasła
void elsword()
{
  char clean;
  if(enterpass())
  {
    lcd.clear();
    lcd.print("NEW PASS:");
    lcd.setCursor(0,1);
    change = true;
    for(int i = 0; i<20; i++)
    {
     newpass[i] = clean;
    }
    digitalWrite(10, LOW);
  }
  else 
  {
    count = 0;
    cls();
    password.reset();
  }
}

//Anulowanie wpisywania hasła
void cancel()
{
    change = false;
    count = 0;
    password.reset();
    cls();
    digitalWrite(9, LOW);
    digitalWrite(8, LOW);
    digitalWrite(10, HIGH);
}

//Zmiana hasła
void changepass()
{
  char clean;
    for(int i=0; i<count; i++)
    {
      temp[i] = newpass[i]; 
    }
    for(int i=0; i<20; i++)
    {
      newpass[i] = clean; 
    }
    for(int i=0; i<count; i++)
    {
      newpass[i] = temp[i]; 
    }
      change = false; 
      password.set(newpass);
      lcd.clear();
      lcd.print("CHANGED!");
      delay(500);
      password.reset();
      count = 0;
      cls();
      for(int i=0; i<count; i++)
    {
      temp[i] = clean; 
    }
    digitalWrite(10, HIGH);
}

//Usuń znak
void deletechoice()
{
  if(change)
  {
    lcd.clear();
    lcd.print("NEW PASS:");
    lcd.setCursor(0,1);
  }
  else cls();
    if(count > 0) count--;
    for(int i=0; i<count; i++)
    {
        lcd.print("*");
    }
}

//Zmiana zapisanej karty
void elsys()
{
  char cler;
  char clean;
  if(enterpass())
  {
    int clean;
    for(int i = 0; i < 4; i++) card[i] = clean;
    lcd.clear();
    lcd.print("AWAITING CARD");
    while(!rfio.PICC_IsNewCardPresent());
    for(int i=0; i<rfio.uid.size;i++)
    {
      card[i]=rfio.uid.uidByte[i];
    }
    lcd.clear();
    lcd.print("CARD CHANGED!");
    delay(2000);
    cls();
    count = 0;
    rfio.PICC_HaltA();
  rfio.PCD_StopCrypto1();
  password.reset();
  }
  else 
  {
    count = 0;
    cls();
    password.reset();
  }
  for(int i = 0; i<20; i++)
    {
      pass[i] = cler;
    }
}

//Obsługa wciśniętych guziczków
void keypadEvent(KeypadEvent key)
{
    switch(keypad.getState())
    {
        case PRESSED:
        if('A' != key && '*' != key && 'C' != key && 'D' != key && 'B' != key && '#' != key) lcd.print("*");  //NIE ZAPOMNIEĆ ZMIENIĆ NA GWIAZDKI
        switch(key)
        {
            case 'A': if(!change) confirmpass(); break;
            case 'B': cancel(); break;
            case 'C': 
            {
              if(change && count != 0)changepass();
              else if(!change) elsword();
            } break;
            case 'D': deletechoice(); break;
            case '*': break;
            case '#': elsys(); break;
            default: 
            {
              //password.append(key);
              if(change) 
              {
                newpass[count] = key;
                count++;
              }
              else
              {
                pass[count] = key;
                count++;
              }
            }
        }
    }
}

void cls()
{
    lcd.clear();
    lcd.print("PASSWORD:");
    lcd.setCursor(0,1);
}