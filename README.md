# API para cadastro de árvore genealógica

Java RESTful API criada para o Bootcamp Santander DIO

## Diagrama de Classes

``` mermaid

classDiagram
    class Person {
        -name: String
        -birthdayDate: String
        -nickname: String
        -partner: String
        -children: List<Person>
        +Person(name: String, birthdayDate: String, nickname: String, partner: String, children: List<Person>)
        +getName(): String
        +getBirthdayDate(): String
        +getNickname(): String
        +getPartner(): String
        +getChildren(): List<Person>
    }

    Person --> Person : has children

```
