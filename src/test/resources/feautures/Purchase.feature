@tag
Feature: Purchase the Order from Ecommerce the website
  Here you can add any description

  Background :
    Given
    When
    Then

  @tag2
  Scenario Outline: Positive test of Submitting the order
    Given  Logging with <username> and <password>
    When  I add the product to the cart <productname>
    And   Checkout <productname> is displayed
    Then  <message> is diplayed
    Examples:
      | username | password  | productname  | message
      | foutouhabderrahman8@gmail.com  |nN1Q41]pXV2r|"ZARA COAT 3"|THANK YOU FOR YOUR ORDER |
      | abdou@gmail.com |aUsAEC2Z8GsntPRz|ADIDAS ORIGINAL| THANK YOU FOR YOUR ORDER |