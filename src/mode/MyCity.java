package mode;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class MyCity extends Application {
     private static List<City> mCities = new ArrayList<City>();
     City city =new City();
   
     public static void addCity(City city){
    	 mCities.add(city);
     }
     public static void removeCity(City city){
    	 for(City c:mCities){
    		 if(city.getName().equals(c.getName()))
    			 mCities.remove(c);
    	 }
     }
     public static List<City> getAllCity(){
    	 return mCities;
     }
     public static void romoveCityFromName(String s){
    	  for(City city :mCities){
    		  if(city.getName().equals(s))
    			  mCities.remove(city);
    	  }
     }

}
