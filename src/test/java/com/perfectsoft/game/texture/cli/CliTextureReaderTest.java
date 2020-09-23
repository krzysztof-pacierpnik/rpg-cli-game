package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CliTextureReaderTest {

    private final static List<CliCharPoint> EXP_WARRIOR_STAND_POINTS = List.of(
            new CliCharPoint(4,2, (byte)'X'),
            new CliCharPoint(0,3, (byte)'X'), new CliCharPoint(1,3, (byte)'X'), new CliCharPoint(3,3, (byte)'X'), new CliCharPoint(4,3, (byte)'X'), new CliCharPoint(5,3, (byte)'X'), new CliCharPoint(7,3, (byte)'X'), new CliCharPoint(8,3, (byte)'X'),
            new CliCharPoint(0,4, (byte)'X'), new CliCharPoint(1,4, (byte)'X'), new CliCharPoint(2,4, (byte)'X'), new CliCharPoint(6,4, (byte)'X'), new CliCharPoint(7,4, (byte)'X'),
            new CliCharPoint(0,5, (byte)'X'), new CliCharPoint(2,5, (byte)'X'), new CliCharPoint(3,5, (byte)'X'), new CliCharPoint(5,5, (byte)'X'), new CliCharPoint(6,5, (byte)'X'),
            new CliCharPoint(3,6, (byte)'X'), new CliCharPoint(4,6, (byte)'X'), new CliCharPoint(5,6, (byte)'X')
    );

    @Test
    public void readWarriorStand() {

        Collection<CliCharPoint> actualPoints = CliTextureReader.readPoints("warrior_stand",
                new CliPoint(0,0));
        final List<CliCharPoint> actualNonTransientPoints = actualPoints.stream()
                .filter(point -> point.getCharacter() != CliTexture.TRANSPARENT_CHARACTER)
                .collect(Collectors.toList());
        Assertions.assertThat(actualNonTransientPoints).containsExactlyElementsOf(EXP_WARRIOR_STAND_POINTS);
    }

    @Test
    public void readWarriorStandTex() {

        Texture texture = CliTextureReader.readTexture("warrior_stand", new CliPoint(0,0));
        Assertions.assertThat(texture.getSize()).isEqualTo(new CliPoint(9,9));
        Assertions.assertThat(texture.getMiddlePosition()).isEqualTo(new CliPoint(5,5));
    }

    @Test
    public void readWarriorStandTexTemp() {

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture texture = textureTemp.rotateMoveTo(Direction.UP, new CliPoint(0,0));
        Assertions.assertThat(texture.getSize()).isEqualTo(new CliPoint(9,9));
        Assertions.assertThat(texture.getMiddlePosition()).isEqualTo(new CliPoint(0,0));
        Assertions.assertThat(texture.getUpperLeftCornerPosition()).isEqualTo(new CliCharPoint(-4,-4, (byte)' '));
    }
}
