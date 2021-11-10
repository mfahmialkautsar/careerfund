package id.careerfund.api.utils.converters;

import id.careerfund.api.domains.entities.Role;

import java.util.ArrayList;
import java.util.Collection;

public class ModelConverter  {
    public static Collection<String> toStringRoleList (Collection<Role> roles) {
        Collection<String> stringCollection = new ArrayList<>();

        for (Role role: roles) {
            stringCollection.add(role.getName().name());
        }
        return stringCollection;
    }
}
