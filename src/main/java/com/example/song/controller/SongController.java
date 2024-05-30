package com.example.song.controller;

import java.util.*;

import com.example.song.service.SongH2Service;
import com.example.song.model.Song;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class SongController {
    @Autowired
    public SongH2Service shs;

    @GetMapping("/songs")
    public ArrayList<Song> getSongs() {
        return shs.getSongs();
    }

    @GetMapping("/songs/{songId}")
    public Song gettingSong(@PathVariable("songId") int songId) {
        return shs.gettingSong(songId);
    }

    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        return shs.addSong(song);
    }

    @PutMapping("/songs/{songId}")
    public Song updateSong(@PathVariable("songId") int songId, @RequestBody Song song) {
        return shs.updateSong(songId, song);
    }

    @DeleteMapping("/songs/{songId}")
    public void deleteSong(@PathVariable int songId) {
        shs.deleteSong(songId);
    }

}