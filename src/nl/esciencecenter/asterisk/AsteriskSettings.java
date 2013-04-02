package nl.esciencecenter.asterisk;

import nl.esciencecenter.asterisk.data.GlueSceneDescription;
import nl.esciencecenter.esight.util.Settings;
import nl.esciencecenter.esight.util.TypedProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsteriskSettings extends Settings {
    private final static Logger logger = LoggerFactory
            .getLogger(AsteriskSettings.class);

    private final double STAR_DEFAULT_LUMINOSITY = 3000.0;

    private static class SingletonHolder {
        public final static AsteriskSettings instance = new AsteriskSettings();
    }

    private final String[] ACCEPTABLE_EXTENSIONS = new String[] { "gas", "bin" };

    private final float MIN_GAS_DENSITY = 0f;
    private final float MAX_GAS_DENSITY = 15f;

    // Minimum and maximum values for the brightness sliders
    private float POSTPROCESSING_OVERALL_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_OVERALL_BRIGHTNESS_MAX = 10f;
    private float POSTPROCESSING_AXES_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_AXES_BRIGHTNESS_MAX = 10f;
    private float POSTPROCESSING_POINT_GAS_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_POINT_GAS_BRIGHTNESS_MAX = 10f;
    private final float POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MIN = 0f;
    private final float POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MAX = 10f;
    private float POSTPROCESSING_STAR_HALO_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_STAR_HALO_BRIGHTNESS_MAX = 10f;
    private float POSTPROCESSING_STAR_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_STAR_BRIGHTNESS_MAX = 10f;
    private float POSTPROCESSING_SPHERE_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_SPHERE_BRIGHTNESS_MAX = 10f;

    private final float PARTICLE_SIZE_MULTIPLIER_MIN = 1f;
    private float PARTICLE_SIZE_MULTIPLIER_DEF = 10f;
    private final float PARTICLE_SIZE_MULTIPLIER_MAX = 100f;

    private final int OCTREE_LOD_MIN = 1;
    private int OCTREE_LOD_DEF = 5;
    private final int OCTREE_LOD_MAX = 25;

    private final float OCTREE_DENSITY_MIN = 1f;
    private float OCTREE_DENSITY_DEF = 10f;
    private final float OCTREE_DENSITY_MAX = 100f;

    private float POSTPROCESSING_HUD_BRIGHTNESS_MIN = 0f;
    private float POSTPROCESSING_HUD_BRIGHTNESS_MAX = 10f;
    // Settings for the postprocessing shader
    private float POSTPROCESSING_OVERALL_BRIGHTNESS_DEF = 10f;
    private float POSTPROCESSING_AXES_BRIGHTNESS_DEF = 1f;
    private float POSTPROCESSING_POINT_GAS_BRIGHTNESS_DEF = 1f;
    private float POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_DEF = 1f;
    private float POSTPROCESSING_STAR_HALO_BRIGHTNESS_DEF = 1f;
    private float POSTPROCESSING_STAR_BRIGHTNESS_DEF = 1f;
    private float POSTPROCESSING_SPHERE_BRIGHTNESS_DEF = 1f;

    private float POSTPROCESSING_HUD_BRIGHTNESS_DEF = 3f;
    // Settings for the star-shape blur method (the + shape of stars)
    private int STAR_SHAPE_BLUR_SIZE = 1;
    private float STAR_SHAPE_BLURFILTER_SIZE = 8f;
    private float STAR_SHAPE_SIGMA = 100f;
    private float STAR_SHAPE_ALPHA = 0.5f;

    private int STAR_SHAPE_BLUR_TYPE = 0;
    // Settings for the detail levels.
    // private int LEVEL_OF_DETAIL = 0;

    private final int SLIDER_MIN_BLUR_TYPE = 1;
    private final int SLIDER_DEF_BLUR_TYPE = 1;
    private final int SLIDER_MAX_BLUR_TYPE = 8;

    private final int SLIDER_MIN_BLUR_PASSES = 0;
    private final int SLIDER_DEF_BLUR_PASSES = 1;
    private final int SLIDER_MAX_BLUR_PASSES = 2;

    private final int SLIDER_MIN_BLUR_SIZE = 2;
    private final int SLIDER_DEF_BLUR_SIZE = 2;
    private final int SLIDER_MAX_BLUR_SIZE = 8;

    private int LOW_STAR_HALO_BLUR_PASSES = 1;
    private float LOW_STAR_HALO_BLUR_SIZE = 1;
    private int LOW_STAR_HALO_BLUR_TYPE = 6;

    private int LOW_GAS_SUBDIVISION = 0;
    private int LOW_STAR_SUBDIVISION = 1;

    private int LOW_GAS_PARTICLES_PER_OCTREE_NODE = 100;

    private int MEDIUM_STAR_HALO_BLUR_PASSES = 1;
    private float MEDIUM_STAR_HALO_BLUR_SIZE = 1;
    private int MEDIUM_STAR_HALO_BLUR_TYPE = 6;

    private int MEDIUM_GAS_SUBDIVISION = 1;
    private int MEDIUM_STAR_SUBDIVISION = 2;

    private int MEDIUM_GAS_PARTICLES_PER_OCTREE_NODE = 25;

    private int HIGH_STAR_HALO_BLUR_PASSES = 2;
    private float HIGH_STAR_HALO_BLUR_SIZE = 1;
    private int HIGH_STAR_HALO_BLUR_TYPE = 6;

    private int HIGH_GAS_SUBDIVISION = 1;
    private int HIGH_STAR_SUBDIVISION = 3;

    private int HIGH_GAS_PARTICLES_PER_OCTREE_NODE = 5;
    // Snaphots have different settings, since they are rendered at extremely
    // high resolutions pixels
    private int SNAPSHOT_GAS_BLUR_PASSES = 2; // 2
    private float SNAPSHOT_GAS_BLUR_SIZE = 2; // 6
    private int SNAPSHOT_GAS_BLUR_TYPE = 8; // 10

    private int SNAPSHOT_STAR_HALO_BLUR_PASSES = 2; // 2
    private float SNAPSHOT_STAR_HALO_BLUR_SIZE = 1; // 1
    private int SNAPSHOT_STAR_HALO_BLUR_TYPE = 6; // 6

    private boolean GAS_COLOR_INVERTED = false;
    private boolean GAS_COLOR_BACKGROUND_INVERTED = false;
    private boolean GAS_COLOR_FROM_STARS = false;

    private boolean STAR_COLORS_EXAGGERATED = true;

    private long WAITTIME_FOR_RETRY = 10000;
    private long WAITTIME_FOR_MOVIE = 200;
    private float EPSILON = 1.0E-7f;

    private float GAS_OPACITY_FACTOR_MIN = 0f;
    private float GAS_OPACITY_FACTOR_DEF = 1f;
    private float GAS_OPACITY_FACTOR_MAX = 2f;

    private static final int GAS_OPACITY_RATIO_MIN = 1;
    private static final int GAS_OPACITY_RATIO_MAX = 100;

    private static final boolean OCTREE_RANDOM_OFFSET = false;

    private boolean BEZIER_INTERPOLATION = false;
    private int BEZIER_INTERPOLATION_STEPS = 10;

    private int PREPROCESSING_AMOUNT = 5;

    private int blurTypeSetting;
    private int blurPassSetting;
    private int blurSizeSetting;

    private float gasOpacityRatio = 10;

    public static final int MAX_ELEMENTS = 10;
    public static final int MAX_OCTREE_DEPTH = 25;

    public static final float INITIAL_OCTREE_SIZE = 500f;

    public static final int STAR_SUBDIVISION = 2;
    public static final int PLANET_SUBDIVISION = 2;
    public static final int SPHERE_SUBDIVISION = 2;

    public static final int OCTREE_MODEL_SUBDIVISION = 2;

    private GlueSceneDescription currentDescription;

    public static AsteriskSettings getInstance() {
        return SingletonHolder.instance;
    }

    private final String currentExtension = "bin";
    private final int timeStep = 1;

    private AsteriskSettings() {
        super();

        try {
            final TypedProperties props = new TypedProperties();
            props.loadFromFile("settings.properties");

            // Minimum and maximum values for the brightness sliders
            POSTPROCESSING_OVERALL_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_OVERALL_BRIGHTNESS_MIN");
            POSTPROCESSING_OVERALL_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_OVERALL_BRIGHTNESS_MAX");
            POSTPROCESSING_AXES_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_AXES_BRIGHTNESS_MIN");
            POSTPROCESSING_AXES_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_AXES_BRIGHTNESS_MAX");
            POSTPROCESSING_POINT_GAS_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_POINT_GAS_BRIGHTNESS_MIN");
            POSTPROCESSING_POINT_GAS_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_POINT_GAS_BRIGHTNESS_MAX");
            POSTPROCESSING_POINT_GAS_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MIN");
            POSTPROCESSING_POINT_GAS_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MAX");
            POSTPROCESSING_STAR_HALO_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_STAR_HALO_BRIGHTNESS_MIN");
            POSTPROCESSING_STAR_HALO_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_STAR_HALO_BRIGHTNESS_MAX");
            POSTPROCESSING_STAR_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_STAR_BRIGHTNESS_MIN");
            POSTPROCESSING_STAR_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_STAR_BRIGHTNESS_MAX");
            POSTPROCESSING_SPHERE_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_SPHERE_BRIGHTNESS_MIN");
            POSTPROCESSING_SPHERE_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_SPHERE_BRIGHTNESS_MAX");

            POSTPROCESSING_HUD_BRIGHTNESS_MIN = props
                    .getFloatProperty("POSTPROCESSING_HUD_BRIGHTNESS_MIN");
            POSTPROCESSING_HUD_BRIGHTNESS_MAX = props
                    .getFloatProperty("POSTPROCESSING_HUD_BRIGHTNESS_MAX");

            // Settings for the postprocessing shader
            POSTPROCESSING_OVERALL_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_OVERALL_BRIGHTNESS_DEF");
            POSTPROCESSING_AXES_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_AXES_BRIGHTNESS_DEF");
            POSTPROCESSING_POINT_GAS_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_POINT_GAS_BRIGHTNESS_DEF");
            POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_DEF");
            POSTPROCESSING_STAR_HALO_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_STAR_HALO_BRIGHTNESS_DEF");
            POSTPROCESSING_STAR_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_STAR_BRIGHTNESS_DEF");
            POSTPROCESSING_SPHERE_BRIGHTNESS_DEF = props
                    .getFloatProperty("POSTPROCESSING_SPHERE_BRIGHTNESS_DEF");

            // Settings for the star-shape blur method (the + shape of stars)
            STAR_SHAPE_BLUR_SIZE = props.getIntProperty("STAR_SHAPE_BLUR_SIZE");
            STAR_SHAPE_BLURFILTER_SIZE = props
                    .getFloatProperty("STAR_SHAPE_BLURFILTER_SIZE");
            STAR_SHAPE_SIGMA = props.getFloatProperty("STAR_SHAPE_SIGMA");
            STAR_SHAPE_ALPHA = props.getFloatProperty("STAR_SHAPE_ALPHA");
            STAR_SHAPE_BLUR_TYPE = props.getIntProperty("STAR_SHAPE_BLUR_TYPE");

            // Settings for the detail levels.
            // LEVEL_OF_DETAIL = props.getIntProperty("LEVEL_OF_DETAIL");

            // LOW_GAS_BLUR_PASSES =
            // props.getIntProperty("LOW_GAS_BLUR_PASSES");
            // LOW_GAS_BLUR_SIZE = props.getFloatProperty("LOW_GAS_BLUR_SIZE");
            // LOW_GAS_BLUR_TYPE = props.getIntProperty("LOW_GAS_BLUR_TYPE");

            LOW_STAR_HALO_BLUR_PASSES = props
                    .getIntProperty("LOW_STAR_HALO_BLUR_PASSES");
            LOW_STAR_HALO_BLUR_SIZE = props
                    .getFloatProperty("LOW_STAR_HALO_BLUR_SIZE");
            LOW_STAR_HALO_BLUR_TYPE = props
                    .getIntProperty("LOW_STAR_HALO_BLUR_TYPE");

            LOW_GAS_SUBDIVISION = props.getIntProperty("LOW_GAS_SUBDIVISION");
            LOW_STAR_SUBDIVISION = props.getIntProperty("LOW_STAR_SUBDIVISION");
            LOW_GAS_PARTICLES_PER_OCTREE_NODE = props
                    .getIntProperty("LOW_GAS_PARTICLES_PER_OCTREE_NODE");

            // MEDIUM_GAS_BLUR_PASSES = props
            // .getIntProperty("MEDIUM_GAS_BLUR_PASSES");
            // MEDIUM_GAS_BLUR_SIZE = props
            // .getFloatProperty("MEDIUM_GAS_BLUR_SIZE");
            // MEDIUM_GAS_BLUR_TYPE =
            // props.getIntProperty("MEDIUM_GAS_BLUR_TYPE");

            MEDIUM_STAR_HALO_BLUR_PASSES = props
                    .getIntProperty("MEDIUM_STAR_HALO_BLUR_PASSES");
            MEDIUM_STAR_HALO_BLUR_SIZE = props
                    .getFloatProperty("MEDIUM_STAR_HALO_BLUR_SIZE");
            MEDIUM_STAR_HALO_BLUR_TYPE = props
                    .getIntProperty("MEDIUM_STAR_HALO_BLUR_TYPE");

            MEDIUM_GAS_SUBDIVISION = props
                    .getIntProperty("MEDIUM_GAS_SUBDIVISION");
            MEDIUM_STAR_SUBDIVISION = props
                    .getIntProperty("MEDIUM_STAR_SUBDIVISION");
            MEDIUM_GAS_PARTICLES_PER_OCTREE_NODE = props
                    .getIntProperty("MEDIUM_GAS_PARTICLES_PER_OCTREE_NODE");

            // HIGH_GAS_BLUR_PASSES =
            // props.getIntProperty("HIGH_GAS_BLUR_PASSES");
            // HIGH_GAS_BLUR_SIZE =
            // props.getFloatProperty("HIGH_GAS_BLUR_SIZE");
            // HIGH_GAS_BLUR_TYPE = props.getIntProperty("HIGH_GAS_BLUR_TYPE");

            HIGH_STAR_HALO_BLUR_PASSES = props
                    .getIntProperty("HIGH_STAR_HALO_BLUR_PASSES");
            HIGH_STAR_HALO_BLUR_SIZE = props
                    .getFloatProperty("HIGH_STAR_HALO_BLUR_SIZE");
            HIGH_STAR_HALO_BLUR_TYPE = props
                    .getIntProperty("HIGH_STAR_HALO_BLUR_TYPE");

            HIGH_GAS_SUBDIVISION = props.getIntProperty("HIGH_GAS_SUBDIVISION");
            HIGH_STAR_SUBDIVISION = props
                    .getIntProperty("HIGH_STAR_SUBDIVISION");
            HIGH_GAS_PARTICLES_PER_OCTREE_NODE = props
                    .getIntProperty("HIGH_GAS_PARTICLES_PER_OCTREE_NODE");

            // Snaphots have different settings, since they are rendered at
            // extremely
            // high resolutions pixels
            SNAPSHOT_GAS_BLUR_PASSES = props
                    .getIntProperty("SNAPSHOT_GAS_BLUR_PASSES");
            SNAPSHOT_GAS_BLUR_SIZE = props
                    .getFloatProperty("SNAPSHOT_GAS_BLUR_SIZE");
            SNAPSHOT_GAS_BLUR_TYPE = props
                    .getIntProperty("SNAPSHOT_GAS_BLUR_TYPE");

            SNAPSHOT_STAR_HALO_BLUR_PASSES = props
                    .getIntProperty("SNAPSHOT_STAR_HALO_BLUR_PASSES");
            SNAPSHOT_STAR_HALO_BLUR_SIZE = props
                    .getFloatProperty("SNAPSHOT_STAR_HALO_BLUR_SIZE");
            SNAPSHOT_STAR_HALO_BLUR_TYPE = props
                    .getIntProperty("SNAPSHOT_STAR_HALO_BLUR_TYPE");

            GAS_COLOR_INVERTED = props.getBooleanProperty("GAS_COLOR_INVERTED");
            GAS_COLOR_BACKGROUND_INVERTED = props
                    .getBooleanProperty("GAS_COLOR_BACKGROUND_INVERTED");
            GAS_COLOR_FROM_STARS = props
                    .getBooleanProperty("GAS_COLOR_FROM_STARS");
            STAR_COLORS_EXAGGERATED = props
                    .getBooleanProperty("STAR_COLORS_EXAGGERATED");

            WAITTIME_FOR_RETRY = props.getLongProperty("WAITTIME_FOR_RETRY");
            WAITTIME_FOR_MOVIE = props.getLongProperty("WAITTIME_FOR_MOVIE");
            EPSILON = props.getFloatProperty("EPSILON");

            GAS_OPACITY_FACTOR_MIN = props
                    .getFloatProperty("GAS_OPACITY_FACTOR_MIN");
            GAS_OPACITY_FACTOR_DEF = props
                    .getFloatProperty("GAS_OPACITY_FACTOR_DEF");
            GAS_OPACITY_FACTOR_MAX = props
                    .getFloatProperty("GAS_OPACITY_FACTOR_MAX");

            BEZIER_INTERPOLATION = props
                    .getBooleanProperty("BEZIER_INTERPOLATION");
            BEZIER_INTERPOLATION_STEPS = props
                    .getIntProperty("BEZIER_INTERPOLATION_STEPS");

            PREPROCESSING_AMOUNT = props.getIntProperty("PREPROCESSING_AMOUNT");
        } catch (NumberFormatException e) {
            logger.debug(e.getMessage());
        }

        currentDescription = new GlueSceneDescription(0, 0, "hotres",
                MIN_GAS_DENSITY, MAX_GAS_DENSITY, "");

        // currentDescription = new GlueSceneDescription(0, 0, "default", 0f,
        // 25f);

        blurTypeSetting = SLIDER_DEF_BLUR_TYPE;
        blurPassSetting = SLIDER_DEF_BLUR_PASSES;
        blurSizeSetting = SLIDER_DEF_BLUR_SIZE;
    }

    public GlueSceneDescription getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentSceneDescriptionString(String value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setDescriptionString(value);

        currentDescription = tempDesc;
    }

    public void setCurrentFrameNumber(int value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setFrameNumber(value);

        currentDescription = tempDesc;
    }

    public void setCurrentLOD(int value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setLevelOfDetail(value);

        currentDescription = tempDesc;
    }

    public void setCurrentColorMap(String value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setColorMap(value);

        currentDescription = tempDesc;
    }

    public void setCurrentLowerBound(float value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setLowerBound(value);

        currentDescription = tempDesc;
    }

    public void setCurrentUpperBound(float value) {
        GlueSceneDescription tempDesc = currentDescription.clone();
        tempDesc.setUpperBound(value);

        currentDescription = tempDesc;
    }

    public float getEpsilon() {
        return EPSILON;
    }

    public boolean getGasInvertedBackgroundColor() {
        return GAS_COLOR_BACKGROUND_INVERTED;
    }

    public boolean getGasInvertedColor() {
        return GAS_COLOR_INVERTED;
    }

    public int getGasParticlesPerOctreeNode() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_GAS_PARTICLES_PER_OCTREE_NODE;
        case 1:
            return MEDIUM_GAS_PARTICLES_PER_OCTREE_NODE;
        case 2:
            return HIGH_GAS_PARTICLES_PER_OCTREE_NODE;
        }
        return 0;
    }

    public boolean getGasStarInfluencedColor() {
        return GAS_COLOR_FROM_STARS;
    }

    public int getGasSubdivision() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_GAS_SUBDIVISION;
        case 1:
            return MEDIUM_GAS_SUBDIVISION;
        case 2:
            return HIGH_GAS_SUBDIVISION;
        }
        return 0;
    }

    public float getPostprocessingAxesBrightness() {
        return POSTPROCESSING_AXES_BRIGHTNESS_DEF;
    }

    public float getPostprocessingAxesBrightnessMax() {
        return POSTPROCESSING_AXES_BRIGHTNESS_MAX;
    }

    public float getPostprocessingAxesBrightnessMin() {
        return POSTPROCESSING_AXES_BRIGHTNESS_MIN;
    }

    public float getPostprocessingPointGasBrightness() {
        return POSTPROCESSING_POINT_GAS_BRIGHTNESS_DEF;
    }

    public float getPostprocessingPointGasBrightnessMax() {
        return POSTPROCESSING_POINT_GAS_BRIGHTNESS_MAX;
    }

    public float getPostprocessingPointGasBrightnessMin() {
        return POSTPROCESSING_POINT_GAS_BRIGHTNESS_MIN;
    }

    public float getPostprocessingOctreeGasBrightness() {
        return POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_DEF;
    }

    public float getPostprocessingOctreeGasBrightnessMax() {
        return POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MAX;
    }

    public float getPostprocessingOctreeGasBrightnessMin() {
        return POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_MIN;
    }

    public float getPostprocessingHudBrightness() {
        return POSTPROCESSING_HUD_BRIGHTNESS_DEF;
    }

    public float getPostprocessingHudBrightnessMax() {
        return POSTPROCESSING_HUD_BRIGHTNESS_MAX;
    }

    public float getPostprocessingHudBrightnessMin() {
        return POSTPROCESSING_HUD_BRIGHTNESS_MIN;
    }

    public float getPostprocessingOverallBrightness() {
        return POSTPROCESSING_OVERALL_BRIGHTNESS_DEF;
    }

    public float getPostprocessingOverallBrightnessMax() {
        return POSTPROCESSING_OVERALL_BRIGHTNESS_MAX;
    }

    public float getPostprocessingOverallBrightnessMin() {
        return POSTPROCESSING_OVERALL_BRIGHTNESS_MIN;
    }

    public float getPostprocessingStarBrightness() {
        return POSTPROCESSING_STAR_BRIGHTNESS_DEF;
    }

    public float getPostprocessingStarBrightnessMax() {
        return POSTPROCESSING_STAR_BRIGHTNESS_MAX;
    }

    public float getPostprocessingStarBrightnessMin() {
        return POSTPROCESSING_STAR_BRIGHTNESS_MIN;
    }

    public float getPostprocessingStarHaloBrightness() {
        return POSTPROCESSING_STAR_HALO_BRIGHTNESS_DEF;
    }

    public float getPostprocessingStarHaloBrightnessMax() {
        return POSTPROCESSING_STAR_HALO_BRIGHTNESS_MAX;
    }

    public float getPostprocessingStarHaloBrightnessMin() {
        return POSTPROCESSING_STAR_HALO_BRIGHTNESS_MIN;
    }

    public float getPostprocessingSphereBrightnessMin() {
        return POSTPROCESSING_SPHERE_BRIGHTNESS_MIN;
    }

    public float getPostprocessingSphereBrightnessMax() {
        return POSTPROCESSING_SPHERE_BRIGHTNESS_MAX;
    }

    public float getPostprocessingSphereBrightness() {
        return POSTPROCESSING_SPHERE_BRIGHTNESS_DEF;
    }

    public float getParticleSizeMultiplierMin() {
        return PARTICLE_SIZE_MULTIPLIER_MIN;
    }

    public float getParticleSizeMultiplierMax() {
        return PARTICLE_SIZE_MULTIPLIER_MAX;
    }

    public float getParticleSizeMultiplier() {
        return PARTICLE_SIZE_MULTIPLIER_DEF;
    }

    public int getOctreeLODMin() {
        return OCTREE_LOD_MIN;
    }

    public int getOctreeLODMax() {
        return OCTREE_LOD_MAX;
    }

    public int getOctreeLOD() {
        return OCTREE_LOD_DEF;
    }

    public float getOctreeDensityMin() {
        return OCTREE_DENSITY_MIN;
    }

    public float getOctreeDensityMax() {
        return OCTREE_DENSITY_MAX;
    }

    public float getOctreeDensity() {
        return OCTREE_DENSITY_DEF;
    }

    public int getSnapshotGasBlurPasses() {
        return SNAPSHOT_GAS_BLUR_PASSES;
    }

    public float getSnapshotGasBlurSize() {
        return SNAPSHOT_GAS_BLUR_SIZE;
    }

    public int getSnapshotGasBlurType() {
        return SNAPSHOT_GAS_BLUR_TYPE;
    }

    public int getSnapshotStarHaloBlurPasses() {
        return SNAPSHOT_STAR_HALO_BLUR_PASSES;
    }

    public float getSnapshotStarHaloBlurSize() {
        return SNAPSHOT_STAR_HALO_BLUR_SIZE;
    }

    public int getSnapshotStarHaloBlurType() {
        return SNAPSHOT_STAR_HALO_BLUR_TYPE;
    }

    public boolean getStarColorsExaggerated() {
        return STAR_COLORS_EXAGGERATED;
    }

    public int getStarHaloBlurPasses() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_STAR_HALO_BLUR_PASSES;
        case 1:
            return MEDIUM_STAR_HALO_BLUR_PASSES;
        case 2:
            return HIGH_STAR_HALO_BLUR_PASSES;
        }
        return 0;
    }

    public float getStarHaloBlurSize() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_STAR_HALO_BLUR_SIZE;
        case 1:
            return MEDIUM_STAR_HALO_BLUR_SIZE;
        case 2:
            return HIGH_STAR_HALO_BLUR_SIZE;
        }
        return 0;
    }

    public int getStarHaloBlurType() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_STAR_HALO_BLUR_TYPE;
        case 1:
            return MEDIUM_STAR_HALO_BLUR_TYPE;
        case 2:
            return HIGH_STAR_HALO_BLUR_TYPE;
        }
        return 0;
    }

    public float getStarShapeAlpha() {
        return STAR_SHAPE_ALPHA;
    }

    public float getStarShapeBlurfilterSize() {
        return STAR_SHAPE_BLURFILTER_SIZE;
    }

    public int getStarShapeBlurSize() {
        return STAR_SHAPE_BLUR_SIZE;
    }

    public int getStarShapeBlurType() {
        return STAR_SHAPE_BLUR_TYPE;
    }

    public float getStarShapeSigma() {
        return STAR_SHAPE_SIGMA;
    }

    public int getStarSubdivision() {
        switch (currentDescription.getLevelOfDetail()) {
        case 0:
            return LOW_STAR_SUBDIVISION;
        case 1:
            return MEDIUM_STAR_SUBDIVISION;
        case 2:
            return HIGH_STAR_SUBDIVISION;
        }
        return 0;
    }

    public long getWaitTimeMovie() {
        return WAITTIME_FOR_MOVIE;
    }

    public long getWaitTimeRetry() {
        return WAITTIME_FOR_RETRY;
    }

    public void setGasInvertedBackgroundColor(int stateChange) {
        if (stateChange == 1) {
            GAS_COLOR_BACKGROUND_INVERTED = true;
        }
        if (stateChange == 2) {
            GAS_COLOR_BACKGROUND_INVERTED = false;
        }
    }

    public void setInvertGasColor(int stateChange) {
        if (stateChange == 1) {
            GAS_COLOR_INVERTED = true;
        }
        if (stateChange == 2) {
            GAS_COLOR_INVERTED = false;
        }
    }

    public void setPostprocessingAxesBrightness(float value) {
        POSTPROCESSING_AXES_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingPointGasBrightness(float value) {
        POSTPROCESSING_POINT_GAS_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingOctreeGasBrightness(float value) {
        POSTPROCESSING_OCTREE_GAS_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingHudBrightness(int value) {
        POSTPROCESSING_HUD_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingOverallBrightness(float value) {
        POSTPROCESSING_OVERALL_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingSphereBrightness(float value) {
        POSTPROCESSING_SPHERE_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingStarBrightness(float value) {
        POSTPROCESSING_STAR_BRIGHTNESS_DEF = value;
    }

    public void setPostprocessingStarHaloBrightness(float value) {
        POSTPROCESSING_STAR_HALO_BRIGHTNESS_DEF = value;
    }

    public void setParticleSizeMultiplier(float value) {
        PARTICLE_SIZE_MULTIPLIER_DEF = value;
    }

    public void setOctreeLOD(int value) {
        OCTREE_LOD_DEF = value;
    }

    public void setOctreeDensity(float value) {
        OCTREE_DENSITY_DEF = value;
    }

    public void setStarColorsExaggerated(int stateChange) {
        if (stateChange == 1) {
            STAR_COLORS_EXAGGERATED = true;
        }
        if (stateChange == 2) {
            STAR_COLORS_EXAGGERATED = false;
        }
    }

    public void setStarInfluencedGasColor(int stateChange) {
        if (stateChange == 1) {
            GAS_COLOR_FROM_STARS = true;
        }
        if (stateChange == 2) {
            GAS_COLOR_FROM_STARS = false;
        }
    }

    public void setWaitTimeMovie(long value) {
        WAITTIME_FOR_MOVIE = value;
    }

    public void setGasOpacityFactor(float value) {
        GAS_OPACITY_FACTOR_DEF = value;
    }

    public float getGasOpacityFactorMin() {
        return GAS_OPACITY_FACTOR_MIN;
    }

    public float getGasOpacityFactor() {
        return GAS_OPACITY_FACTOR_DEF;
    }

    public float getGasOpacityFactorMax() {
        return GAS_OPACITY_FACTOR_MAX;
    }

    public void setBezierInterpolation(boolean value) {
        BEZIER_INTERPOLATION = value;
    }

    public boolean getBezierInterpolation() {
        return BEZIER_INTERPOLATION;
    }

    public void setBezierInterpolationSteps(int value) {
        BEZIER_INTERPOLATION_STEPS = value;
    }

    public int getBezierInterpolationSteps() {
        return BEZIER_INTERPOLATION_STEPS;
    }

    public int getPreprocessAmount() {
        return PREPROCESSING_AMOUNT;
    }

    public void setPreprocessAmount(int value) {
        PREPROCESSING_AMOUNT = value;
    }

    public String getCurrentExtension() {
        return currentExtension;
    }

    public int getTimestep() {
        return timeStep;
    }

    public String[] getAcceptableExtensions() {
        return ACCEPTABLE_EXTENSIONS;
    }

    public double getStarDefaultLuminosity() {
        return STAR_DEFAULT_LUMINOSITY;
    }

    public void setVariableRange(int sliderLowerValue, int sliderUpperValue) {
        float diff = MAX_GAS_DENSITY - MIN_GAS_DENSITY;
        float minFloatValue = (sliderLowerValue / 100f) * diff;
        float maxFloatValue = (sliderUpperValue / 100f) * diff;

        GlueSceneDescription result = currentDescription.clone();
        result.setLowerBound(minFloatValue);
        result.setUpperBound(maxFloatValue);
        currentDescription = result;
    }

    public int getRangeSliderLowerValue() {
        float min = MIN_GAS_DENSITY;
        float max = MAX_GAS_DENSITY;
        float currentMin = currentDescription.getLowerBound();

        float diff = max - min;
        float result = (currentMin - min) / diff;

        return (int) (result * 100) - 1;
    }

    public int getRangeSliderUpperValue() {
        float min = MIN_GAS_DENSITY;
        float max = MAX_GAS_DENSITY;
        float currentMax = currentDescription.getUpperBound();

        float diff = max - min;
        float result = (currentMax - min) / diff;

        return (int) (result * 100) - 1;
    }

    public float getMinGasDensity() {
        return 0;
    }

    public float getMaxGasDensity() {
        return MAX_GAS_DENSITY;
    }

    public float getGasOpacityRatio() {
        return gasOpacityRatio;
    }

    public void setGasOpacityRatio(float value) {
        gasOpacityRatio = value;
    }

    public int getBlurTypeSetting() {
        return blurTypeSetting;
    }

    public void setBlurTypeSetting(int blurTypeSetting) {
        this.blurTypeSetting = blurTypeSetting;
    }

    public int getBlurPassSetting() {
        return blurPassSetting;
    }

    public void setBlurPassSetting(int blurPassSetting) {
        this.blurPassSetting = blurPassSetting;
    }

    public int getBlurSizeSetting() {
        return blurSizeSetting;
    }

    public void setBlurSizeSetting(int blurSizeSetting) {
        this.blurSizeSetting = blurSizeSetting;
    }

    public int getBlurTypeMin() {
        return SLIDER_MIN_BLUR_TYPE;
    }

    public int getBlurTypeMax() {
        return SLIDER_MAX_BLUR_TYPE;
    }

    public int getBlurPassMin() {
        return SLIDER_MIN_BLUR_PASSES;
    }

    public int getBlurPassMax() {
        return SLIDER_MAX_BLUR_PASSES;
    }

    public int getBlurSizeMin() {
        return SLIDER_MIN_BLUR_SIZE;
    }

    public int getBlurSizeMax() {
        return SLIDER_MAX_BLUR_SIZE;
    }

    public int getGasOpacityRatioMin() {
        return GAS_OPACITY_RATIO_MIN;
    }

    public int getGasOpacityRatioMax() {
        return GAS_OPACITY_RATIO_MAX;
    }

    public boolean isGasRandomCenterOffset() {
        return OCTREE_RANDOM_OFFSET;
    }

    @Override
    public String getScreenshotPath() {
        return SCREENSHOT_PATH;
    }
}
