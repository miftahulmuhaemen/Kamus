package com.josjos.aseloe.kamus.Model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class KamusModel implements Parcelable {

   @Getter @Setter int id;
  @Setter @Getter private String kunci, kata;

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.kunci);
      dest.writeString(this.kata);
   }

   public KamusModel() {
   }

   public KamusModel(String kunci, String kata) {
       this.kunci = kunci;
       this.kata = kata;
   }

   protected KamusModel(Parcel in) {
      this.id = in.readInt();
      this.kunci = in.readString();
      this.kata = in.readString();
   }

   public static final Parcelable.Creator<KamusModel> CREATOR = new Parcelable.Creator<KamusModel>() {
      @Override
      public KamusModel createFromParcel(Parcel source) {
         return new KamusModel(source);
      }

      @Override
      public KamusModel[] newArray(int size) {
         return new KamusModel[size];
      }
   };
}
