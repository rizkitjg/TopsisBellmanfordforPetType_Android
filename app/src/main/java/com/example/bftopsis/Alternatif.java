package com.example.bftopsis;

import java.util.Comparator;

public class Alternatif{
    String namaAlternatif;
    double skorAlternatif;

    public Alternatif(String namaAlternatif, double skorAlternatif) {
        this.namaAlternatif = namaAlternatif;
        this.skorAlternatif = skorAlternatif;
    }

    public void setNamaAlternatif(String namaAlternatif){
        this.namaAlternatif = namaAlternatif;
    }
    public void setSkorAlternatif(double skorAlternatif){
        this.skorAlternatif = skorAlternatif;
    }

    public String getNamaAlternatif() {
        return this.namaAlternatif;
    }

    public double getSkorAlternatif() {
        return this.skorAlternatif;
    }


    /*public int compareTo(Alternatif alternatif) {
        return Double.compare(alternatif.getSkorAlternatif(), this.skorAlternatif);
    }*/

    @Override
    public String toString() {
        return "Alternatif{" +
                "namaAlternatif='" + namaAlternatif + '\'' +
                ", skorAlternatif=" + skorAlternatif +
                '}';
    }

}
