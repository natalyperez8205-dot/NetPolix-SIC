package dao;

import modelo.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Video_Dao {

    private static final List<Video> videos = new ArrayList<>();
    private static int nextId = 1;

    public void guardarVideo(Video video) {
        video.setId(nextId++);
        videos.add(video);
        System.out.println("Video guardado: " + video.getTituloOriginal());
    }

    public List<Video> listarVideos() {
        return Collections.unmodifiableList(videos);
    }

    public Video buscarPorId(int id) {
        for (Video video : videos) {
            if (video.getId() == id) {
                return video;
            }
        }
        return null;
    }
}
