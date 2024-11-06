package model.common;

public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(Builder adress){
        this.street = adress.street;
        this.city = adress.city;
        this.state = adress.state;
        this.zip = adress.zip;
    }

    public static class Builder {
        private String street;
        private String city;
        private String state;
        private String zip;

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setZip(String zip) {
            this.zip = zip;
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }
}

/*
Address address = new Address.Builder()
        .setCity("Bangalore")
        .setState("Karnataka").build();
*/

