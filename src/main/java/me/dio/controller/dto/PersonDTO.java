package me.dio.controller.dto;

import me.dio.domain.model.Person;

public record PersonDTO (
    Long id,
    String name,
    String birthday,
    String nickname,
    String partner,
    String phone) {

    public PersonDTO(Person model) {
        this(
                model.getId(),
                model.getName(),
                model.getBirthday(),
                model.getNickname(),
                model.getPartner(),
                model.getPhone()
        );
    }

    public Person toModel() {
        Person model = new Person();
        model.setId(this.id);
        model.setName(this.name);
        model.setBirthday(this.birthday);
        model.setNickname(this.nickname);
        model.setPartner(this.partner);
        model.setPhone(this.phone);
        return model;
    }
}
