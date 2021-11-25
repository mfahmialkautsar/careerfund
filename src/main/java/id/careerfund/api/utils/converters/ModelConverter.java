package id.careerfund.api.utils.converters;

import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelConverter {
    public static List<String> toStringRoleList(Collection<Role> roles) {
        List<String> stringList = new ArrayList<>();

        for (Role role : roles) {
            stringList.add(role.getName().name());
        }
        return stringList;
    }

    public static List<Class> toClass(List<Class> classes) {
        List<Class> classList = new ArrayList<>();

        for (Class aClass : classes) {
            classList.add(aClass);
        }
        return classList;
    }
}
