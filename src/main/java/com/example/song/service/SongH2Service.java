package com.example.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import org.springframework.dao.EmptyResultDataAccessException;
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
    public Song addSong(Song song) {
        jd.update("INSERT INTO PLAYLIST(songName, lyricist, singer, musicDirector) VALUES(?, ?, ?, ?)",
                song.getSongName(), song.getLyricist(), song.getSinger(), song.getMusicDirector());
        Song AddingSung = jd.queryForObject(
                "SELECT * FROM PLAYLIST WHERE songName = ? and lyricist = ?",
                new SongRowMapper(), song.getSongName(), song.getLyricist());
        return AddingSung;
    }

    @Override
    public Song updateSong(int songId, Song song) {
        try {
            Song existing = jd.queryForObject("SELECT * FROM PLAYLIST WHERE songId = ?", new SongRowMapper(), songId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (song.getSongName() != null) {
            jd.update("UPDATE PLAYLIST SET songName = ? where songId =?", song.getSongName(), songId);
        }
        if (song.getLyricist() != null) {
            jd.update("UPDATE PLAYLIST SET lyricist = ? where songId =?", song.getLyricist(), songId);
        }
        if (song.getSinger() != null) {
            jd.update("UPDATE PLAYLIST SET singer = ? where songId =?", song.getSinger(), songId);
        }
        if (song.getMusicDirector() != null) {
            jd.update("UPDATE PLAYLIST SET musicDirector = ? where songId =?", song.getMusicDirector(), songId);
        }
        return gettingSong(songId);
    }

    @Override
    public void deleteSong(int songId) {
        jd.update("delete from PLAYLIST WHERE songId = ?", songId);
    }
}
