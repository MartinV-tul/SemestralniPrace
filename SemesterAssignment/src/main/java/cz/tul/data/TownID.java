package cz.tul.data;

import java.io.Serializable;

public class TownID implements Serializable {

    private String name;

    private String countryName;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        return obj.hashCode() == hashCode();
    }
}
