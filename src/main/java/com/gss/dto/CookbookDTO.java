package com.gss.dto;

import com.gss.entity.Cookbook;
import com.gss.entity.Material;
import com.gss.entity.Step;

import java.util.List;

public class CookbookDTO extends Cookbook {

    List<Step> method;
    List<Material> material;

    public List<Step> getMethod() {
        return method;
    }

    public void setMethod(List<Step> method) {
        this.method = method;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }
}
