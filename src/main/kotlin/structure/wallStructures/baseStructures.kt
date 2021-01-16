package structure.wallStructures

import structure.baseStructs

/**
 * All available Structures
 * If a Structure gets Added, it must be added here aswell
 */
internal val baseStructures = baseStructs(
    Curve::class,
    EmptyWallStructure::class,
    Grid::class,
    Helix::class,
    // HelixCurve::class,
    Line::class,
    Helix::class,
    RandomNoisePath::class,
    RandomCurve::class,
    RandomNoise::class,
    WallStructureField::class,
    Wall::class,
)
