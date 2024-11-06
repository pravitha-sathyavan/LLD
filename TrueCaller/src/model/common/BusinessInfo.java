package model.common;

public class BusinessInfo {

    private String name;
    private String description;
    private Address address;
    private String website;

    public BusinessInfo(Builder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.address = builder.address;
        this.website = builder.website;
    }

    public static class Builder{
        private String name;
        private String description;
        private Address address;
        private String website;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder address(Address address){
            this.address = address;
            return this;
        }

        public Builder website(String website){
            this.website = website;
            return this;
        }

        public BusinessInfo build(){
            return new BusinessInfo(this);
        }
    }
}
