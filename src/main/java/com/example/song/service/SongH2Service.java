package com.example.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.song.model.Song;
import com.example.song.model.SongRowMapper;
import com.example.song.repository.SongRepository;

@Service
public class SongH2Service implements SongRepository {
    @Autowired
    public JdbcTemplate jd;

    @Override
    public ArrayList<Song> getSongs() {
        List<Song> songs = jd.query("select * from playlist", new SongRowMapper());
        ArrayList<Song> sung = new ArrayList<>(songs);
        return sung;
    }

    @Override
    public Song gettingSong(int songId) {
        try {
            Song sun = jd.queryForObject("SELECT * FROM PLAYLIST WHERE songId = ?", new SongRowMapper(), songId);
            return sun;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Song addSong(Song addSung) {
        jd.update("INSERT INTO PLAYLIST(songName, lyricist, singer, musicDirector) VALUES(?, ?, ?, ?)",
                addSung.getSongName(), addSung.getLyricist(), addSung.getSinger(), addSung.getMusicDirector());
        Song AddingSung = jd.queryForObject(
                "SELECT * FROM PLAYLIST WHERE songName = ? and lyricist = ? and singer = ? and musicDirector = ?",
                new SongRowMapper(), addSung.getSongName(), addSung.getLyricist(), addSung.getSinger(),
                addSung.getMusicDirector());
        return AddingSung;
    }

    @Override
    public Song updateSong(int songId, Song putSong) {
        if (putSong.getSongName() != null) {
            jd.update("UPDATE PLAYLIST SET songName = ?", putSong.getSongName(), songId);
        }
        if (putSong.getLyricist() != null) {
            jd.update("UPDATE PLAYLIST SET lyricist = ?", putSong.getLyricist(), songId);
        }
        if (putSong.getSinger() != null) {
            jd.update("UPDATE PLAYLIST SET singer = ?", putSong.getSinger(), songId);
        }
        if (putSong.getMusicDirector() != null) {
            jd.update("UPDATE PLAYLIST SET musicDirector = ?", putSong.getMusicDirector(), songId);
        }
        return gettingSong(songId);
    }

    @Override
    public void deleteSong(int songId) {
        jd.update("delete from PLAYLIST WHERE songId = ?", songId);
    }
}