package nl.esciencecenter.asterisk;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import nl.esciencecenter.asterisk.data.GlueTimedPlayer;
import nl.esciencecenter.asterisk.interfaces.TimedPlayer;
import nl.esciencecenter.esight.swing.CustomJSlider;
import nl.esciencecenter.esight.swing.GoggleSwing;

public class AsteriskInterfaceWindow extends JPanel {
    public static enum TweakState {
        NONE, DATA, VISUAL, MOVIE
    }

    private final AsteriskSettings settings = AsteriskSettings.getInstance();

    private static final long serialVersionUID = 1L;

    protected CustomJSlider timeBar;

    protected JFormattedTextField frameCounter;
    private TweakState currentConfigState = TweakState.MOVIE;

    public static TimedPlayer timer;

    private final JPanel configPanel;

    private final JPanel dataConfig, visualConfig, movieConfig;

    public AsteriskInterfaceWindow() {
        setLayout(new BorderLayout(0, 0));

        timeBar = new CustomJSlider(new BasicSliderUI(timeBar));
        timeBar.setValue(0);
        timeBar.setMajorTickSpacing(5);
        timeBar.setMinorTickSpacing(1);
        timeBar.setMaximum(0);
        timeBar.setMinimum(0);
        timeBar.setPaintTicks(true);
        timeBar.setSnapToTicks(true);

        timer = new GlueTimedPlayer(timeBar, frameCounter);

        // Make the menu bar
        final JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        final JMenu options = new JMenu("Options");

        // final JMenuItem makeMovie = new JMenuItem("Make movie.");
        // makeMovie.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        // setTweakState(TweakState.MOVIE);
        // }
        // });
        // options.add(makeMovie);

        final JMenuItem showDataPanel = new JMenuItem("Show data config panel.");
        showDataPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTweakState(TweakState.DATA);
            }
        });
        options.add(showDataPanel);

        final JMenuItem showVisualPanel = new JMenuItem(
                "Show visual config panel.");
        showVisualPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setTweakState(TweakState.VISUAL);
            }
        });
        options.add(showVisualPanel);
        menuBar.add(options);

        menuBar.add(Box.createHorizontalGlue());

        final JMenuBar menuBar2 = new JMenuBar();

        ImageIcon nlescIcon = GoggleSwing.createResizedImageIcon(
                "images/ESCIENCE_logo.jpg", "eScienceCenter Logo", 200, 20);
        JLabel nlesclogo = new JLabel(nlescIcon);
        // nlesclogo.setMinimumSize(new Dimension(300, 20));
        // nlesclogo.setMaximumSize(new Dimension(311, 28));

        menuBar2.add(Box.createHorizontalGlue());
        menuBar2.add(nlesclogo);
        menuBar2.add(Box.createHorizontalGlue());

        Container menuContainer = new Container();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));

        menuContainer.add(menuBar);
        menuContainer.add(menuBar2);

        add(menuContainer, BorderLayout.NORTH);

        // Make the "media player" panel
        final JPanel bottomPanel = createBottomPanel();

        // Add the tweaks panels
        configPanel = new JPanel();
        add(configPanel, BorderLayout.WEST);
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setPreferredSize(new Dimension(240, 0));
        configPanel.setVisible(false);

        dataConfig = new JPanel();
        dataConfig.setLayout(new BoxLayout(dataConfig, BoxLayout.Y_AXIS));
        dataConfig.setMinimumSize(configPanel.getPreferredSize());
        createDataTweakPanel();

        visualConfig = new JPanel();
        visualConfig.setLayout(new BoxLayout(visualConfig, BoxLayout.Y_AXIS));
        visualConfig.setMinimumSize(configPanel.getPreferredSize());
        createVisualTweakPanel();

        movieConfig = new JPanel();
        movieConfig.setLayout(new BoxLayout(movieConfig, BoxLayout.Y_AXIS));
        movieConfig.setMinimumSize(configPanel.getPreferredSize());
        createMovieTweakPanel();

        add(bottomPanel, BorderLayout.SOUTH);

        // Read command line file information
        makeTimer();

        setTweakState(TweakState.VISUAL);

        setVisible(true);
    }

    private JPanel createBottomPanel() {
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setFocusCycleRoot(true);
        bottomPanel.setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container aContainer,
                    Component aComponent) {
                return null;
            }

            @Override
            public Component getComponentBefore(Container aContainer,
                    Component aComponent) {
                return null;
            }

            @Override
            public Component getDefaultComponent(Container aContainer) {
                return null;
            }

            @Override
            public Component getFirstComponent(Container aContainer) {
                return null;
            }

            // No focus traversal here, as it makes stuff go bad (some things
            // react on focus).
            @Override
            public Component getLastComponent(Container aContainer) {
                return null;
            }
        });

        final JButton oneForwardButton = GoggleSwing.createImageButton(
                "images/media-playback-oneforward.png", "Next", null);
        final JButton oneBackButton = GoggleSwing.createImageButton(
                "images/media-playback-onebackward.png", "Previous", null);
        final JButton rewindButton = GoggleSwing.createImageButton(
                "images/media-skip-backward.png", "Rewind", null);
        final JButton screenshotButton = GoggleSwing.createImageButton(
                "images/camera.png", "Screenshot", null);
        final JButton playButton = GoggleSwing.createImageButton(
                "images/media-playback-start.png", "Start", null);
        final ImageIcon playIcon = GoggleSwing.createImageIcon(
                "images/media-playback-start.png", "Start");
        final ImageIcon stopIcon = GoggleSwing.createImageIcon(
                "images/media-playback-stop.png", "Start");

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        final JPanel buttonPanel = new JPanel();
        final JPanel timebarPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        timebarPanel.setLayout(new BoxLayout(timebarPanel, BoxLayout.X_AXIS));

        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // timer.stop();
                // final ImauInputHandler inputHandler = ImauInputHandler
                // .getInstance();
                // final String fileName = "screenshot: " + "{"
                // + inputHandler.getRotation().get(0) + ","
                // + inputHandler.getRotation().get(1) + " - "
                // + Float.toString(inputHandler.getViewDist()) + "} ";
                timer.setScreenshotNeeded(true);
            }
        });
        buttonPanel.add(screenshotButton);

        buttonPanel.add(GoggleSwing.horizontalStrut(2));

        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.rewind();
                playButton.setIcon(playIcon);
            }
        });
        buttonPanel.add(rewindButton);

        buttonPanel.add(GoggleSwing.horizontalStrut(2));

        oneBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.oneBack();
                playButton.setIcon(playIcon);
            }
        });
        buttonPanel.add(oneBackButton);

        buttonPanel.add(GoggleSwing.horizontalStrut(2));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isPlaying()) {
                    timer.stop();
                    playButton.setIcon(playIcon);
                } else {
                    timer.start();
                    playButton.setIcon(stopIcon);
                }
            }
        });
        buttonPanel.add(playButton);

        buttonPanel.add(GoggleSwing.horizontalStrut(2));

        oneForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.oneForward();
                playButton.setIcon(playIcon);
            }
        });
        buttonPanel.add(oneForwardButton);

        buttonPanel.add(GoggleSwing.horizontalStrut(20));

        frameCounter = new JFormattedTextField();
        frameCounter.setValue(new Integer(1));
        frameCounter.setColumns(4);
        frameCounter.setMaximumSize(new Dimension(40, 20));
        frameCounter.setValue(0);
        frameCounter.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                final JFormattedTextField source = (JFormattedTextField) e
                        .getSource();
                if (source.hasFocus()) {
                    if (source == frameCounter) {
                        if (timer.isInitialized()) {
                            timer.setFrame(
                                    ((Number) frameCounter.getValue())
                                            .intValue() - timeBar.getMinimum(),
                                    false);
                        }
                        playButton.setIcon(playIcon);
                    }
                }
            }
        });

        buttonPanel.add(frameCounter);

        timeBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.isFocusOwner() && !source.getValueIsAdjusting()) {
                    timer.setFrame(timeBar.getValue(), false);
                    playButton.setIcon(playIcon);
                }
            }
        });
        timebarPanel.add(timeBar);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(timebarPanel);

        return bottomPanel;
    }

    private void createMovieTweakPanel() {
        final ItemListener listener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                setTweakState(TweakState.NONE);
            }
        };
        movieConfig.add(GoggleSwing.titleBox("Movie Creator", listener));

        final ItemListener checkBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.setMovieRotate(e.getStateChange());
                timer.redraw();
            }
        };
        movieConfig.add(GoggleSwing.checkboxBox(
                "",
                new GoggleSwing.CheckBoxItem("Rotation", settings
                        .getMovieRotate(), checkBoxListener)));

        final JLabel rotationSetting = new JLabel(""
                + settings.getMovieRotationSpeedDef());
        final ChangeListener movieRotationSpeedListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setMovieRotationSpeed(source.getValue() * .25f);
                    rotationSetting.setText(""
                            + settings.getMovieRotationSpeedDef());
                }
            }
        };
        movieConfig.add(GoggleSwing.sliderBox("Rotation Speed",
                movieRotationSpeedListener,
                (int) (settings.getMovieRotationSpeedMin() * 4f),
                (int) (settings.getMovieRotationSpeedMax() * 4f), 1,
                (int) (settings.getMovieRotationSpeedDef() * 4f),
                rotationSetting));

        movieConfig.add(GoggleSwing.buttonBox("",
                new GoggleSwing.ButtonBoxItem("Start Recording",
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                timer.movieMode();
                            }
                        })));
    }

    private void createDataTweakPanel() {
        final ItemListener listener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                setTweakState(TweakState.NONE);
            }
        };
        dataConfig.add(GoggleSwing.titleBox("Data Configuration", listener));

        dataConfig.add(GoggleSwing.verticalStrut(1));

        // dataConfig.add(GoggleSwing.radioBox("Level of Detail", new String[] {
        // "Low", "Medium", "High" },
        // new ActionListener[] { new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        // settings.setCurrentLOD(0);
        // timer.redraw();
        // }
        // }, new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        // settings.setCurrentLOD(1);
        // timer.redraw();
        // }
        // }, new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent arg0) {
        // settings.setCurrentLOD(2);
        // timer.redraw();
        // }
        // } }, "Low"));

        final ChangeListener octreeLODSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setOctreeLOD(source.getValue());
                }
            }
        };
        dataConfig.add(GoggleSwing.sliderBox(
                "Octree Detail (lower = slower&better)",
                octreeLODSliderListener, (settings.getOctreeLODMin()),
                (settings.getOctreeLODMax()), 1, (settings.getOctreeLOD()),
                new JLabel("")));

        dataConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener octreeDensitySliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setOctreeDensity(source.getValue());
                }
            }
        };
        dataConfig.add(GoggleSwing.sliderBox("Octree Density cutoff.",
                octreeDensitySliderListener,
                (int) (settings.getOctreeDensityMin()),
                (int) (settings.getOctreeDensityMax()), 1,
                (int) (settings.getOctreeDensity()), new JLabel("")));

        dataConfig.add(GoggleSwing.verticalStrut(1));

        // final JComboBox comboBoxColorMaps =
        // ColormapInterpreter.getLegendJComboBox(new Dimension(200, 25));
        //
        // GlueSceneDescription description = settings.getCurrentDescription();
        //
        // comboBoxColorMaps.setSelectedItem(ColormapInterpreter.getIndexOfColormap(description.getColorMap()));
        //
        // comboBoxColorMaps.setMinimumSize(new Dimension(200, 25));
        // comboBoxColorMaps.setMaximumSize(new Dimension(200, 25));
        // dataConfig.add(comboBoxColorMaps);
        //
        // dataConfig.add(GoggleSwing.verticalStrut(1));
        //
        // final RangeSlider legendSlider = new RangeSlider();
        // ((RangeSliderUI)
        // legendSlider.getUI()).setRangeColorMap(description.getColorMap());
        // legendSlider.setMinimum(0);
        // legendSlider.setMaximum(100);
        // legendSlider.setValue(settings.getRangeSliderLowerValue());
        // legendSlider.setUpperValue(settings.getRangeSliderUpperValue());
        //
        // final String[] colorMaps = ColormapInterpreter.getColormapNames();
        //
        // final RangeSliderUI frs = ((RangeSliderUI) legendSlider.getUI());
        // comboBoxColorMaps.addItemListener(new ItemListener() {
        // @Override
        // public void itemStateChanged(ItemEvent e) {
        // settings.setCurrentColorMap(colorMaps[comboBoxColorMaps.getSelectedIndex()]);
        //
        // frs.setRangeColorMap(colorMaps[comboBoxColorMaps.getSelectedIndex()]);
        // }
        // });
        // legendSlider.addChangeListener(new ChangeListener() {
        // @Override
        // public void stateChanged(ChangeEvent e) {
        // RangeSlider slider = (RangeSlider) e.getSource();
        // if (!slider.getValueIsAdjusting()) {
        // settings.setVariableRange(slider.getValue(), slider.getUpperValue());
        // }
        // }
        // });
        // dataConfig.add(legendSlider);

        dataConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener particleSizeMultiplierListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setParticleSizeMultiplier(source.getValue());
                }
            }
        };
        dataConfig.add(GoggleSwing.sliderBox("Particle size multiplier.",
                particleSizeMultiplierListener,
                (settings.getParticleSizeMultiplierMin()),
                (settings.getParticleSizeMultiplierMax()), 1,
                (int) (settings.getParticleSizeMultiplier()), new JLabel("")));

    }

    private void createVisualTweakPanel() {
        final ItemListener listener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                setTweakState(TweakState.NONE);
            }
        };
        visualConfig
                .add(GoggleSwing.titleBox("Visual Configuration", listener));

        // final ItemListener cblBeamerMode = new ItemListener() {
        // @Override
        // public void itemStateChanged(ItemEvent e) {
        // settings.setInvertGasColor(e.getStateChange());
        // timer.redraw();
        // }
        // };
        // final ItemListener cblInvertedBackground = new ItemListener() {
        // @Override
        // public void itemStateChanged(ItemEvent e) {
        // settings.setGasInvertedBackgroundColor(e.getStateChange());
        // timer.redraw();
        // }
        // };
        // final ItemListener cblStereo = new ItemListener() {
        // @Override
        // public void itemStateChanged(ItemEvent e) {
        // settings.setStereo(e.getStateChange());
        // timer.redraw();
        // }
        // };
        // final ItemListener cblStereoSwitch = new ItemListener() {
        // @Override
        // public void itemStateChanged(ItemEvent e) {
        // settings.setStereoSwitched(e.getStateChange());
        // timer.redraw();
        // }
        // };
        // visualConfig
        // .add(GoggleSwing.checkboxBox("",
        // new GoggleSwing.CheckBoxItem("Beamer mode",
        // settings.getGasInvertedColor(), cblBeamerMode),
        // new GoggleSwing.CheckBoxItem("White background",
        // settings.getGasInvertedBackgroundColor(),
        // cblInvertedBackground),
        // new GoggleSwing.CheckBoxItem("Stereo view", settings.getStereo(),
        // cblStereo),
        // new GoggleSwing.CheckBoxItem("Stereo left/right switch",
        // settings.getStereoSwitched(),
        // cblStereoSwitch)));

        final ChangeListener overallBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingOverallBrightness(source
                            .getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Overall Brightness",
                overallBrightnessSliderListener,
                (int) (settings.getPostprocessingOverallBrightnessMin()),
                (int) (settings.getPostprocessingOverallBrightnessMax()), 1,
                (int) (settings.getPostprocessingOverallBrightness()),
                new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener axesBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingAxesBrightness(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Axes Brightness",
                axesBrightnessSliderListener, (int) (settings
                        .getPostprocessingAxesBrightnessMin()), (int) (settings
                        .getPostprocessingAxesBrightnessMax()), 1,
                (int) (settings.getPostprocessingAxesBrightness()), new JLabel(
                        "")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener hudBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingHudBrightness(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("HUD Brightness",
                hudBrightnessSliderListener, (int) (settings
                        .getPostprocessingHudBrightnessMin()), (int) (settings
                        .getPostprocessingHudBrightnessMax()), 1,
                (int) (settings.getPostprocessingHudBrightness()), new JLabel(
                        "")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener pointGasBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingPointGasBrightness(source
                            .getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Point Gas Brightness",
                pointGasBrightnessSliderListener,
                (int) (settings.getPostprocessingPointGasBrightnessMin()),
                (int) (settings.getPostprocessingPointGasBrightnessMax()), 1,
                (int) (settings.getPostprocessingPointGasBrightness()),
                new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener octreeGasBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingOctreeGasBrightness(source
                            .getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Octree Gas Brightness",
                octreeGasBrightnessSliderListener,
                (int) (settings.getPostprocessingOctreeGasBrightnessMin()),
                (int) (settings.getPostprocessingOctreeGasBrightnessMax()), 1,
                (int) (settings.getPostprocessingOctreeGasBrightness()),
                new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener starHaloBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingStarHaloBrightness(source
                            .getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Star Halo Brightness",
                starHaloBrightnessSliderListener,
                (int) (settings.getPostprocessingStarHaloBrightnessMin()),
                (int) (settings.getPostprocessingStarHaloBrightnessMax()), 1,
                (int) (settings.getPostprocessingStarHaloBrightness()),
                new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener starBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingStarBrightness(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Star Brightness",
                starBrightnessSliderListener, (int) (settings
                        .getPostprocessingStarBrightnessMin()), (int) (settings
                        .getPostprocessingStarBrightnessMax()), 1,
                (int) (settings.getPostprocessingStarBrightness()), new JLabel(
                        "")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener sphereBrightnessSliderListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setPostprocessingSphereBrightness(source
                            .getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Sphere Brightness",
                sphereBrightnessSliderListener,
                (int) (settings.getPostprocessingSphereBrightnessMin()),
                (int) (settings.getPostprocessingSphereBrightnessMax()), 1,
                (int) (settings.getPostprocessingSphereBrightness()),
                new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener blurTypeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setBlurTypeSetting(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Blur Type", blurTypeListener,
                settings.getBlurTypeMin(), settings.getBlurTypeMax(), 1,
                settings.getBlurTypeSetting(), new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener blurPassListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setBlurPassSetting(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Blur Passes", blurPassListener,
                settings.getBlurPassMin(), settings.getBlurPassMax(), 1,
                settings.getBlurPassSetting(), new JLabel("")));

        visualConfig.add(GoggleSwing.verticalStrut(1));

        final ChangeListener blurSizeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                final JSlider source = (JSlider) e.getSource();
                if (source.hasFocus()) {
                    settings.setBlurSizeSetting(source.getValue());
                }
            }
        };
        visualConfig.add(GoggleSwing.sliderBox("Blur Size", blurSizeListener,
                settings.getBlurSizeMin(), settings.getBlurSizeMax(), 1,
                settings.getBlurSizeSetting(), new JLabel("")));

        // final ChangeListener stereoOcularDistanceListener = new
        // ChangeListener() {
        // @Override
        // public void stateChanged(ChangeEvent e) {
        // final JSlider source = (JSlider) e.getSource();
        // if (source.hasFocus()) {
        // settings.setStereoOcularDistance(source.getValue() / 10f);
        // }
        // }
        // };
        // visualConfig.add(GoggleSwing.sliderBox("Stereo Ocular Distance",
        // stereoOcularDistanceListener,
        // (int) (settings.getStereoOcularDistanceMin() * 10), (int)
        // (settings.getStereoOcularDistanceMax() * 10),
        // 1, (int) (settings.getStereoOcularDistance() * 10), new JLabel("")));
        //
        // visualConfig.add(GoggleSwing.verticalStrut(1));

    }

    private void makeTimer() {
        if (timer.isInitialized()) {
            timer.close();
        }
        timer = new GlueTimedPlayer(timeBar, frameCounter);

    }

    // Callback methods for the various ui actions and listeners
    public void setTweakState(TweakState newState) {
        configPanel.setVisible(false);
        configPanel.remove(dataConfig);
        configPanel.remove(visualConfig);
        // configPanel.remove(movieConfig);

        currentConfigState = newState;

        if (currentConfigState == TweakState.NONE) {
        } else if (currentConfigState == TweakState.DATA) {
            configPanel.setVisible(true);
            configPanel.add(dataConfig, BorderLayout.WEST);
        } else if (currentConfigState == TweakState.VISUAL) {
            configPanel.setVisible(true);
            configPanel.add(visualConfig, BorderLayout.WEST);
            // } else if (currentConfigState == TweakState.MOVIE) {
            // configPanel.setVisible(true);
            // configPanel.add(movieConfig, BorderLayout.WEST);
        }
    }

    public static TimedPlayer getTimer() {
        return timer;
    }
}
