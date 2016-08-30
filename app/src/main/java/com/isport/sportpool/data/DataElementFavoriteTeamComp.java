package com.isport.sportpool.data;

import java.util.Comparator;




public class DataElementFavoriteTeamComp implements Comparator<DataElementFavoriteTeam>{
		 
	    public int compare(DataElementFavoriteTeam e1, DataElementFavoriteTeam e2) {
	        if(e1.getteamCode().equals(e2.getteamCode())){
	            return 0;
	        } else {
	            return -1;
	        }
	    }
}
