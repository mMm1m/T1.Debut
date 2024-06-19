package org.example.task_1.enums;

public enum ConstStrings {
    STRING_OF_ROLES("{\"roles\":[\"Системный аналитик\",\"Разработчик Java\",\"Разработчик JS/React\",\"Тестировщик\",\"Прикладной администратор\"]}"),
    SYSTEM_ANALYTIC("Системный аналитик"),
    JAVA_DEVELOPER("Разработчик JAVA"),
    FRONTENDER("Разработчик JS/React"),
    TESTER("Тестировщик"),
    APPLIED_ADMIN("Прикладной администратор"),
    EMAIL_REGEX("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.(com|ru)$");
    private String str;
    public String getCode(){ return str;}
    ConstStrings(String str){
        this.str = str;
    }
}
