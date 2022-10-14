//package ui;
//
//// cite most of the method in this class from
//// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
//
//
//import sun.audio.AudioData;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
//import sun.audio.ContinuousAudioDataStream;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//// cite most of the method in this class from
//// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
//
//
//public class Music {
//    public static void music() {
//        AudioPlayer mgp = AudioPlayer.player;
//        AudioStream bgm;
//        AudioData md;
//
//        ContinuousAudioDataStream loop = null;
//
//        try {
//            InputStream test = new FileInputStream("./data/alarm.wav");
//            bgm = new AudioStream(test);
//            AudioPlayer.player.start(bgm);
//            md = bgm.getData();
//            loop = new ContinuousAudioDataStream(md);
//
//        } catch (FileNotFoundException e) {
//            System.out.print(e.toString());
//        } catch (IOException error) {
//            System.out.print(error.toString());
//        }
//        mgp.start(loop);
//    }
//}
