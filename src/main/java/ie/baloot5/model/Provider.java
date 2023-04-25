package ie.baloot5.model;


import com.google.gson.annotations.SerializedName;

public class Provider {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("registryDate")
    private String registryDate;

    public Provider(long id, String name, String registryDate) {
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public Provider(Provider provider) {
        this(provider.id, provider.name, provider.registryDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return id == provider.id && name.equals(provider.name) && registryDate.equals(provider.registryDate);
    }

}