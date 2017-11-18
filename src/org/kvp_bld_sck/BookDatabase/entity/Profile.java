package org.kvp_bld_sck.BookDatabase.entity;

import java.util.Date;

public class Profile {

    private long id;
    private String fullName;
    private Sex sex;
    private Date birthDate;
    private String characteristics;
    private SecurityLevel securityLevel;

    public Profile() {
    }

    public Profile(long id) {
        this.id = id;
    }

    public Profile(String fullName, Sex sex, Date birthDate, String characteristics, SecurityLevel securityLevel) {
        this.fullName = fullName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.characteristics = characteristics;
        this.securityLevel = securityLevel;
    }

    public Profile(long id, String fullName, Sex sex, Date birthDate, String characteristics, SecurityLevel securityLevel) {
        this.id = id;
        this.fullName = fullName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.characteristics = characteristics;
        this.securityLevel = securityLevel;
    }

    public long getId() {
        return id;
    }

    public Profile setId(long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Profile setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public Profile setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Profile setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public Profile setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
        return this;
    }

    public SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

    public Profile setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
        return this;
    }


    public enum SecurityLevel {
        PUBLIC,
        SECRET,
        TOP_SECRET;

        public static SecurityLevel getByName(String levelName) {
            levelName = levelName.toLowerCase();
            for (SecurityLevel securityLevel : values())
                if (securityLevel.toString().toLowerCase().equals(levelName))
                    return securityLevel;
            return TOP_SECRET;
        }
    }

    public enum Sex {
        MALE,
        FEMALE,
        OTHER;

        public static Sex getByName(String sexName) {
            sexName = sexName.toLowerCase();
            for (Sex sex : values())
                if (sex.toString().toLowerCase().equals(sexName))
                    return sex;
            return OTHER;
        }
    }

}
