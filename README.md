# ONFFLINE

## 소스 파일 위치
```
src/
```

## 프로그램 실행 방법
1. 터미널을 이용하여 압축이 풀어진 경로로 이동합니다.
2. 터미널에 다음과 같은 명령어를 입력하여 실행합니다.
   ```bash
   java -jar --module-path lib/ --add-modules javafx.controls,javafx.fxml -Djava.library.path=opencv4/ Onffline4.jar Main
   ```

## 추가 사항
- 컴파일 명령어는 복잡하기 때문에 포함하지 않고, 실행가능한 jar파일로 미리 빌드하여 포함시켰습니다.
- 실행에 문제가 발생한다면 warp26@ajou.ac.kr 로 메일 부탁드립니다.