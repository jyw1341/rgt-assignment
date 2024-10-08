### [원티드, 정용우, 백엔드] 과제제출

시스템에서는 사용자 로그인 후 매장 선택과 식당 테이블 번호 입력을 통해 주문을 진행할 수 있는 기능이 필요합니다. 그러나 현재 개발 단계에서는 매장 선택과 식당 테이블 번호 입력 과정이 구현되지 않았기 때문에, 로그인 요청 시 매장 아이디와 식당 테이블 번호를 함께 보내 세션에 저장하도록 구현하였습니다.

향후, 매장 선택 및 테이블 번호 입력 기능이 구현되면, 사용자는 로그인 이후에 해당 정보를 선택할 수 있게 될 것입니다. 이 경우, 로그인 요청 시 매장 아이디와 테이블 번호를 함께 전송하는 대신, 로그인 후 별도의 선택 절차를 통해 정보를 입력하고 이를 세션에 저장하도록 변경할 계획입니다.

### 테스트 용으로 저장된 데이터

- 회원

| 이름 | 이메일 | 비밀번호 |
| --- | --- | --- |
| tester1 | test1@gmail.com | 1234 |
| tester2 | test2@gmail.com | 1234 |
| tester3 | test3@gmail.com | 1234 |
- 식당

| 아이디 | 식당 이름 | 테이블 수 |
| --- | --- | --- |
| 123 | 큭큭 피자 | 10 |
- 메뉴

| 식당 아이디 | 메뉴 이름 | 메뉴 가격 | 메뉴 설명 |
| --- | --- | --- | --- |
| 123 | 감자밭 피자 | 25000 | 감자 |
| 123 | 통통 새우 피자 | 25000 | 새우 |
| 123 | 베이컨 피자 | 25000 | 베이컨 |

### 로그인 API 구현

로그인 API 엔드포인트(`/login`)는 사용자 인증과 세션 관리를 처리하는 핵심 기능을 제공합니다. 클라이언트가 로그인 정보를 포함한 POST 요청을 보내면, 서버는 다음과 같은 절차를 수행합니다:

1. **사용자 인증**: 요청 본문에서 이메일과 비밀번호를 추출하여 `AuthService`를 통해 사용자를 인증합니다. 이메일로 사용자 정보를 조회하고, 입력된 비밀번호와 저장된 비밀번호를 비교하여 인증을 수행합니다. 비밀번호가 일치하지 않거나 사용자가 존재하지 않는 경우, `LoginFailException` 예외를 발생시킵니다.
2. **세션 관리**: 인증이 성공하면, 새로운 세션을 생성하고 로그인 정보를 저장합니다. `LoginInfo` 객체를 생성하여 사용자 ID, 이름, 매장 ID, 테이블 번호를 세션에 저장하고, 이후 요청에서 이 정보를 통해 사용자의 로그인 상태를 유지합니다.
3. **응답 반환**: 로그인 성공 후, 인증된 사용자의 정보를 포함하는 `LoginSuccessResponse` 객체를 생성하여 클라이언트에 반환합니다. 이를 통해 클라이언트는 로그인 상태와 사용자 정보를 확인할 수 있습니다.

### 주문 API 구현

- **주문 처리 기능**:
    - `OrderService` 클래스에서 `addOrder` 메소드를 통해 주문을 처리합니다. 이 메소드는 주문 요청을 받고, 로그인 정보를 기반으로 현재 테이블과 레스토랑의 주문 상태를 확인한 후, 새로운 주문을 생성하거나 기존 주문을 업데이트합니다.
    - 주문이 없는 경우 새로운 주문을 생성하고, 주문 항목을 추가하는 로직을 구현하여 주문 내역을 효율적으로 처리합니다.
- **주문 내역 관리**:
    - 주문 내역은 테이블 단위로 합산되어 표시되며, 최신 `CONFIRM` 상태의 주문만을 반환하도록 설계되었습니다. 이는 사용자가 실제로 확인할 수 있는 가장 최근의 유효한 주문 정보를 제공하여, 주문 내역의 정확성을 유지합니다.
- **로그인하지 않은 사용자 접근 제한**:
    - `LoginArgumentResolver` 클래스를 통해 커스텀 어노테이션 `@Login`을 사용하여 컨트롤러 메소드에서 로그인 정보를 자동으로 주입하는 기능을 구현했습니다.
    - `OrderController`의 `addOrder` 메소드에서 `@Login` 어노테이션을 사용하여 로그인 정보를 인자로 받아서 주문 처리를 진행합니다.
    - 로그인하지 않은 사용자는 API를 호출할 수 없도록 설계되어 있습니다. `@Login` 어노테이션을 사용한 인자 주입과 `LoginArgumentResolver`를 통해 로그인 상태를 확인하고, 로그인 정보가 없으면 적절한 응답을 반환합니다.
