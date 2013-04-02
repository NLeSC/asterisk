package nl.esciencecenter.asterisk.data;

import java.io.IOException;

import javax.media.opengl.GL3;
import javax.swing.JFormattedTextField;

import nl.esciencecenter.asterisk.AsteriskSettings;
import nl.esciencecenter.asterisk.Snapshot;
import nl.esciencecenter.asterisk.interfaces.SceneStorage;
import nl.esciencecenter.asterisk.interfaces.TimedPlayer;
import nl.esciencecenter.esight.input.InputHandler;
import nl.esciencecenter.esight.math.VecF3;
import nl.esciencecenter.esight.swing.CustomJSlider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlueTimedPlayer implements TimedPlayer {
    private final GlueDatasetManager dsManager;
    private final GlueSceneStorage sceneStorage;

    private states currentState = states.UNOPENED;
    private final static Logger logger = LoggerFactory
            .getLogger(GlueTimedPlayer.class);

    private final AsteriskSettings settings = AsteriskSettings.getInstance();
    private int frameNumber;

    private final boolean running = true;
    private boolean initialized = false;
    private boolean fileLessMode = false;

    private long startTime, stopTime;

    private final CustomJSlider timeBar;
    private final JFormattedTextField frameCounter;

    private final InputHandler inputHandler;

    private boolean needsScreenshot = false;
    private String screenshotFilename = "";

    private final long waittime = settings.getWaitTimeMovie();

    public GlueTimedPlayer(CustomJSlider timeBar,
            JFormattedTextField frameCounter) {
        this.timeBar = timeBar;
        this.frameCounter = frameCounter;
        this.inputHandler = InputHandler.getInstance();
        this.dsManager = new GlueDatasetManager(1, 4);
        this.sceneStorage = dsManager.getSceneStorage();
    }

    @Override
    public void init() {
        this.fileLessMode = true;
        initialized = true;
    }

    @Override
    public void run() {
        if (!initialized && !fileLessMode) {
            System.err.println("HDFTimer started while not initialized.");
            System.exit(1);
        }

        inputHandler.setRotation(new VecF3(settings.getInitialRotationX(),
                settings.getInitialRotationY(), 0f));
        inputHandler.setViewDist(settings.getInitialZoom());

        int frame = settings.getInitialSimulationFrame();
        updateFrame(frame, true);

        stop();

        while (running) {
            if ((currentState == states.PLAYING)
                    || (currentState == states.REDRAWING)
                    || (currentState == states.MOVIEMAKING)) {
                try {
                    if (!isScreenshotNeeded()) {
                        startTime = System.currentTimeMillis();

                        if (currentState == states.MOVIEMAKING) {
                            final VecF3 rotation = inputHandler.getRotation();
                            if (settings.getMovieRotate()) {
                                rotation.set(
                                        1,
                                        rotation.get(1)
                                                + settings
                                                        .getMovieRotationSpeedDef());
                                inputHandler.setRotation(rotation);

                                setScreenshotNeeded(true);
                            } else {
                                setScreenshotNeeded(true);
                            }
                        }

                        // Forward frame
                        if (currentState != states.REDRAWING) {
                            int newFrameNumber;
                            try {
                                newFrameNumber = dsManager
                                        .getNextFrameNumber(frameNumber);
                                if (sceneStorage.doneWithLastRequest()) {
                                    updateFrame(newFrameNumber, false);
                                }
                            } catch (IOException e) {
                                // currentState = states.WAITINGONFRAME;
                                // stop();
                                logger.debug("nextFrame returned IOException.");
                            }
                        }

                        // Wait for the _rest_ of the timeframe
                        stopTime = System.currentTimeMillis();
                        long spentTime = stopTime - startTime;

                        if (spentTime < waittime) {
                            Thread.sleep(waittime - spentTime);
                        }
                    }
                } catch (final InterruptedException e) {
                    System.err.println("Interrupted while playing.");
                }
            } else if (currentState == states.STOPPED) {
                try {
                    Thread.sleep(100);
                } catch (final InterruptedException e) {
                    System.err.println("Interrupted while stopped.");
                }
            } else if (currentState == states.REDRAWING) {
                currentState = states.STOPPED;
            } else if (currentState == states.WAITINGONFRAME) {
                try {
                    Thread.sleep(10);
                } catch (final InterruptedException e) {
                    System.err.println("Interrupted while waiting.");
                }
            }
        }
    }

    public synchronized void addScene(Snapshot scene) {
        dsManager.addScene(scene);

        timeBar.setMaximum(dsManager.getNumFrames() - 1);
        timeBar.invalidate();

        if (currentState == states.WAITINGONFRAME) {
            start();
        }
    }

    @Override
    public synchronized void setFrame(int value, boolean overrideUpdate) {
        stop();

        try {
            updateFrame(dsManager.getFrameNumberOfIndex(value), overrideUpdate);
        } catch (IndexNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void updateFrame(int newFrameNumber,
            boolean overrideUpdate) {
        if (dsManager != null) {
            if (newFrameNumber != frameNumber || overrideUpdate) {

                frameNumber = newFrameNumber;
                settings.setCurrentFrameNumber(newFrameNumber);

                this.timeBar.setValue(dsManager
                        .getIndexOfFrameNumber(newFrameNumber));
                this.frameCounter.setValue(dsManager
                        .getIndexOfFrameNumber(newFrameNumber));
            }
        }
    }

    @Override
    public synchronized void oneBack() {
        stop();

        try {
            int newFrameNumber = dsManager.getPreviousFrameNumber(frameNumber);
            updateFrame(newFrameNumber, false);
        } catch (IOException e) {
            logger.debug("one back failed.");
        }
    }

    @Override
    public synchronized void oneForward() {
        stop();

        try {
            int newFrameNumber = dsManager.getNextFrameNumber(frameNumber);
            updateFrame(newFrameNumber, false);
        } catch (IOException e) {
            logger.debug("one forward failed.");
        }
    }

    @Override
    public synchronized void redraw() {
        if (initialized) {
            updateFrame(frameNumber, true);
            currentState = states.REDRAWING;
        }
    }

    @Override
    public synchronized void rewind() {
        stop();
        updateFrame(0, false);
    }

    @Override
    public SceneStorage getSceneStorage() {
        return sceneStorage;
    }

    @Override
    public synchronized void start() {
        currentState = states.PLAYING;
    }

    @Override
    public synchronized void stop() {
        currentState = states.STOPPED;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public synchronized boolean isPlaying() {
        if ((currentState == states.PLAYING)
                || (currentState == states.MOVIEMAKING)) {
            return true;
        }

        return false;
    }

    @Override
    public synchronized void movieMode() {
        currentState = states.MOVIEMAKING;
    }

    @Override
    public synchronized void setScreenshotNeeded(boolean value) {
        if (value) {
            screenshotFilename = String.format(
                    "screenshots" + System.getProperty("file.separator")
                            + "%05d", (frameNumber))
                    + ".png";

        }
        needsScreenshot = value;
    }

    @Override
    public synchronized boolean isScreenshotNeeded() {
        return needsScreenshot;
    }

    @Override
    public synchronized String getScreenshotFileName() {
        return screenshotFilename;
    }

    @Override
    public void close() {
        initialized = false;
        frameNumber = 0;
        timeBar.setValue(0);
        frameCounter.setValue(0);
        timeBar.setMaximum(0);
    }

    @Override
    public int getFrameNumber() {
        return frameNumber;
    }

    @Override
    public void delete(GL3 gl) {
        // TODO Auto-generated method stub
    }
}
