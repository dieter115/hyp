package be.flashapps.hyp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;


public class Recipe2 {
    @PrimaryKey
    private int id;
    @SerializedName("Naam")
    @Expose
    private String naam;
    @SerializedName("Categorie")
    @Expose
    private String categorie;
    @SerializedName("Afbeelding")
    @Expose
    private String afbeelding;
    @SerializedName("Tijdlijn")
    @Expose
    private String tijdlijn;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Prijs")
    @Expose
    private String prijs;
    @SerializedName("Moeilijkheidsgraad")
    @Expose
    private String moeilijkheidsgraad;
    @SerializedName("Voorbereidingstijd")
    @Expose
    private String voorbereidingstijd;
    @SerializedName("Bereidingstijd")
    @Expose
    private String bereidingstijd;
    @SerializedName("Ingrediënten")
    @Expose
    private String ingrediënten;



    public Recipe2() {

    }


    public Recipe2(int id, String name, String afbeelding){
        setId(id);
        setNaam(name);
        setAfbeelding(afbeelding);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Object getAfbeelding() {
        return afbeelding;
    }

    public void setAfbeelding(String afbeelding) {
        this.afbeelding = afbeelding;
    }

    public String getTijdlijn() {
        return tijdlijn;
    }

    public void setTijdlijn(String tijdlijn) {
        this.tijdlijn = tijdlijn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrijs() {
        return prijs;
    }

    public void setPrijs(String prijs) {
        this.prijs = prijs;
    }

    public String getMoeilijkheidsgraad() {
        return moeilijkheidsgraad;
    }

    public void setMoeilijkheidsgraad(String moeilijkheidsgraad) {
        this.moeilijkheidsgraad = moeilijkheidsgraad;
    }

    public String getVoorbereidingstijd() {
        return voorbereidingstijd;
    }

    public void setVoorbereidingstijd(String voorbereidingstijd) {
        this.voorbereidingstijd = voorbereidingstijd;
    }

    public String getBereidingstijd() {
        return bereidingstijd;
    }

    public void setBereidingstijd(String bereidingstijd) {
        this.bereidingstijd = bereidingstijd;
    }

    public String getingrediënten() {
        return ingrediënten;
    }

    public void setingrediënten(String ingrediënten) {
        this.ingrediënten = ingrediënten;
    }




}