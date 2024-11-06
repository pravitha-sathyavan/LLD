package model.common;

public class PersonalInfo {
    private String firstName;
    private String lastName;
    private String dob;
    private Gender gender;
    private Address address;
    private String companyName;

    public PersonalInfo(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
        this.gender = builder.gender;
        this.companyName = builder.companyName;
        this.address = builder.address;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String dob;
        private Gender gender;
        private String companyName;
        private Address address;

        public Builder(String firstName) {
            this.firstName = firstName;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public PersonalInfo build() {
            return new PersonalInfo(this);
        }
    }

//    PersonalInfo personalInfo = new Builder("Pravitha")
//            .lastName("Sathyavan")
//            .gender(Gender.FEMALE)
//            .build();

}


