## Highlights myself ✏️

### 1. Develop Environment

- **Ktor**
- **Kotlin**
- **MySQL**
- **Gradle Kotlin DSL**

### 2. Install & Run

#### a. Git clone 또는 프로젝트를 다운받으세요.

```text
git clone https://github.com/khcho0125/highlights_myself.git
```

#### b. Gradle을 빌드합니다.

```text
$ ./gradlew build
```

#### c. 환경변수를 설정합니다.

   - [template.env](https://github.com/khcho0125/highlights_myself/blob/main/docs/template.env)파일 양식에 맞춰 환경변수를 입력한 후, 실행 구성에서 환경 변수 파일로 지정합니다.

   - 실행 구성에서 환경 변수를 작성합니다.

> 자신의 데이터베이스 설정과 맞게 입력해야 합니다.

**d. 실행 후 브라우저에 [http://localhost:8080](http://localhost:8080)을 입력하여** `Help People Get Smart Faster` **문구를 확인합니다.**

### 3. Entity Relation Diagram

ERD는 `ERDCloud`를 활용해 작성했습니다.

![ERD](https://user-images.githubusercontent.com/82090641/235293212-28256b09-cd71-4a5d-8a85-c65e13edba11.png)

#### 기본키 구성
* `tbl_user`, `tbl_highlight`, `tbl_collection`의 PrimaryKey → Auto Increment 
* `tbl_highlight_storage`의 PrimaryKey → `tbl_highlight`와 `tbl_collection`의 복합키로 구성

#### 연관 관계

* `tbl_user` - `tbl_highlight` => OneToMany ( 1 to N )
* `tbl_user` - `tbl_collection` => OneToMany ( 1 to N )
* `tbl_highlight` - `tbl_collection` => `tbl_highlight_storage`를 통한 ManyToMany ( N to N )
* `tbl_collection` - `tbl_collection` => OneToMany ( 1 to N ) **self-relationship**

#### ✏️ 추가적인 DB Table 및 Index 정보는 [tables_structure.sql](https://github.com/khcho0125/highlights_myself/blob/main/docs/tables_structure.sql) 파일을 참고해주세요!

### 4. API Specification

> #### [명세 링크](https://documenter.getpostman.com/view/17245687/2s93eR5GBS)입니다.

#### Postman을 사용해 간단한 명세를 작성했습니다.

### 5. Trouble Shooting

마주친 문제는 [Issues](https://github.com/khcho0125/highlights_myself/issues)에 기록해두었습니다.


