package pickit.com.pickit.Adapters;

import java.util.ArrayList;
import java.util.Comparator;

import pickit.com.pickit.Data.PIGenreData;
import pickit.com.pickit.Data.PIPlaceData;

/**
 * Created by or on 29/09/2017.
 */

public class PIPlacesComperator implements Comparator<PIPlaceData> {
    ArrayList<PIGenreData> userFavoriteGenres;

    public PIPlacesComperator(ArrayList<PIGenreData> userFavoriteGenres) {
        this.userFavoriteGenres = userFavoriteGenres;
    }


    @Override
    public int compare(PIPlaceData placeData1, PIPlaceData placeData2) {
        double placeData1MatchScore = 0;
        double placeData2MatchScore = 0;

        for(PIGenreData favoriteGenre : userFavoriteGenres){
            int indexOfGenreInPlace1 = isGenreInList(favoriteGenre, placeData1.genresList);
            int indexOfGenreInPlace2 = isGenreInList(favoriteGenre, placeData2.genresList);

            if(indexOfGenreInPlace1 != -1){
                PIGenreData placeGenre = placeData1.genresList.get(indexOfGenreInPlace1);
                placeData1MatchScore += (favoriteGenre.percentage * placeGenre.percentage)/100;
            }

            if(indexOfGenreInPlace2 != -1){
                PIGenreData placeGenre = placeData2.genresList.get(indexOfGenreInPlace2);
                placeData2MatchScore += (favoriteGenre.percentage * placeGenre.percentage)/100;
            }

        }
        if(placeData1MatchScore > placeData2MatchScore){
            return -1;
        }
        else if (placeData2MatchScore > placeData1MatchScore){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     *
     * @param genre - the genre that we search
     * @param list - the list that we search in
     * @return - return -1 if it is no foud in the list, if it is in the list returns its index
     */
    private int isGenreInList(PIGenreData genre, ArrayList<PIGenreData> list){

        for(PIGenreData g : list){
            if(genre.name.equals(g.name)){
                return list.indexOf(g);
            }
        }

        return -1;
    }
}
