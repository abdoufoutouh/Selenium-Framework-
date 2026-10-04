@tag
Feature: Purchase the Order from Ecommerce the website
  Here you can add any description

  Background:
    Given I landed on Ecomerce Page

  @tag2
  Scenario Outline: Positive test of Submitting the order
    Given Logging with <username> and <password>
    When I add product <productname> to Cart
    And Checkout <productname> is displayed
    Then <message> is diplayed
    Examples:
      | username                      | password         | productname     | message                 |
      | foutouhabderrahman8@gmail.com | nN1Q41]pXV2r     | ZARA COAT 3     | THANKYOU FOR THE ORDER. |
      | abdou@gmail.com               | aUsAEC2Z8GsntPRz | ADIDAS ORIGINAL | THANKYOU FOR THE ORDER. |