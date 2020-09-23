package com.perfectsoft.game.texture.cli;

import com.perfectsoft.game.physics.Direction;
import com.perfectsoft.game.texture.Texture;
import com.perfectsoft.game.texture.TextureTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CliTextureTest {

    private final static List<CliCharPoint> EXP_WARRIOR_STAND_POINTS = List.of(
            new CliCharPoint(3, 0, (byte) 'X'),
            new CliCharPoint(3, 1, (byte) 'X'), new CliCharPoint(4, 1, (byte) 'X'),
            new CliCharPoint(4, 2, (byte) 'X'), new CliCharPoint(5, 2, (byte) 'X'),
            new CliCharPoint(3, 3, (byte) 'X'), new CliCharPoint(5, 3, (byte) 'X'), new CliCharPoint(6, 3, (byte) 'X'),
            new CliCharPoint(2, 4, (byte) 'X'), new CliCharPoint(3, 4, (byte) 'X'), new CliCharPoint(6, 4, (byte) 'X'),
            new CliCharPoint(3, 5, (byte) 'X'), new CliCharPoint(5, 5, (byte) 'X'), new CliCharPoint(6, 5, (byte) 'X'),
            new CliCharPoint(4, 6, (byte) 'X'), new CliCharPoint(5, 6, (byte) 'X'),
            new CliCharPoint(3, 7, (byte) 'X'), new CliCharPoint(4, 7, (byte) 'X'),
            new CliCharPoint(3, 8, (byte) 'X'), new CliCharPoint(4, 8, (byte) 'X'), new CliCharPoint(5, 8, (byte) 'X')
    );

    @Mock
    private Supplier<Texture> menuSupplier;
    @Mock
    private Texture screenTexture;

    private final CliStageRenderer cliStageRenderer = new CliStageRenderer(screenTexture, menuSupplier, 1000);

    @Test
    public void warriorRotateMoveTo() {

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture texture = textureTemp.rotateMoveTo(Direction.LEFT, new CliPoint(4, 4), 1);

        System.out.println("Texture:");
        cliStageRenderer.printStageScreen(texture);

        assertThat(texture.getSize()).isEqualTo(new CliPoint(9, 9));
        assertThat(texture.getMiddlePosition()).isEqualTo(new CliPoint(4, 4));
        assertThat(texture.getUpperLeftCornerPosition()).isEqualTo(new CliCharPoint(0, 0, (byte) ' '));

        EXP_WARRIOR_STAND_POINTS.forEach(point -> {
            Optional<Byte> charOpt = texture.getCharAtPoint(point);
            assertThat(charOpt.isPresent()).isTrue();
            assertThat(charOpt.get()).isEqualTo(point.getCharacter());
        });
    }

    @Test
    public void warriorMoveToBound() {

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture texture = textureTemp.moveToBound(new CliPoint(0, 0), new CliPoint(2, 2), new CliPoint(9,9), 1);

        System.out.println("Texture:");
        cliStageRenderer.printStageScreen(texture);

        assertThat(texture.getSize()).isEqualTo(new CliPoint(3, 3));
        assertThat(texture.getMiddlePosition()).isEqualTo(new CliPoint(3, 3));
        assertThat(texture.getUpperLeftCornerPosition()).isEqualTo(new CliCharPoint(2, 2, (byte) ' '));
    }


    @Test
    public void warriorMoveToBound2() {

        TextureTemplate textureTemp = CliTextureReader.readTextureTemplate("warrior_stand");
        Texture texture = textureTemp.moveToBound(new CliPoint(4, 4), new CliPoint(2, 2), new CliPoint(9,9), 1);

        System.out.println("Texture:");
        cliStageRenderer.printStageScreen(texture);

        assertThat(texture.getSize()).isEqualTo(new CliPoint(7, 7));
        assertThat(texture.getMiddlePosition()).isEqualTo(new CliPoint(5, 5));
        assertThat(texture.getUpperLeftCornerPosition()).isEqualTo(new CliCharPoint(2, 2, (byte) ' '));
    }
}
