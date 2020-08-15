package org.itstep.Task2;

public class Service {
        @Value("db.properties")
        private String name;
        private String properties;

    public Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getProperties() {
        return properties;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
