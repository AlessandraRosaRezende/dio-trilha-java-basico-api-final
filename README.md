# API para cadastro de pessoas e data nascimento

Java RESTful API criada para o Bootcamp Santander DIO

## Diagrama de Classes

``` mermaid

classDiagram
    class Person {
        -name: String
        -birthdayDate: String
        -nickname: String
        -partner: String
        -phone: String
        +Person(name: String, birthdayDate: String, nickname: String, partner: String, phone: String)
        +getName(): String
        +getBirthdayDate(): String
        +getNickname(): String
        +getPartner(): String
        +getPhone(): String
    }


```
