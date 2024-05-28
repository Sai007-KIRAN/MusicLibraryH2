package com.example.song.repository;

import java.util.*;
import com.example.song.model.Song;

public interface SongRepository {
    ArrayList<Song> getSongs();

    Song gettingSong(int songId);

    Song addSong(Song addSung);

    Song updateSong(int songId, Song putSong);

    void deleteSong(int songId);
}