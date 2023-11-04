package org.example.models;

import java.io.Serializable;

public class Magic implements Serializable {

    private String id;
    private String name;
    private String power;
//    Description
    private String oracle_text;

    public Magic() {
    }

    public Magic(String id, String name, String power, String oracle_text) {
        this.id = id;
        this.name = name;
        this.power =  power;
        this.oracle_text = oracle_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOracle_text() {
        return oracle_text;
    }

    public void setOracle_text(String oracle_text) {
        this.oracle_text = oracle_text;
    }

    @Override
    public String toString() {
        return """
                Nombre: %s
                Poder: %s
                Descripción: %s
                """.formatted(name, power.isEmpty() ? "No tiene poder" : power, oracle_text.isEmpty() ? "No tiene descripción" : oracle_text);
    }
}
