public class CustomData {
    private String hairShape;
    private String hairColor;
    private String eyeColor;
    private String skinColor;

    public String getHairShape() {
        return hairShape;
    }

    public void setHairShape(String hairShape) {
        this.hairShape = hairShape;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getSkinColor() {
        return skinColor;
    }


    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public CustomData(String hairShape, String hairColor, String eyeColor, String skinColor) {
        this.hairShape = hairShape;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.skinColor = skinColor;
    }
}
