package model.ingredients;

import java.util.List;

public class IngredientsResponse {
    boolean success;
    List <Ingredients> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredients> getData() {
        return data;
    }

    public void setData(List<Ingredients> data) {
        this.data = data;
    }
}
