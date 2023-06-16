package cz.kostka.pochod.service;

import cz.kostka.pochod.enums.DiplomSize;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public final class DiplomCreator {

    private DiplomCreator() {
    }

    public static void download(
            final String username,
            final HttpServletResponse response,
            final DiplomSize diplomSize)
            throws IOException {

        prepareResponseHeaders(username, response);
        final ClassPathResource file = new ClassPathResource("/main/static" +
                "/img/diplom/" + getFileNameForSize(diplomSize));
        BufferedImage diplomImage = ImageIO
                .read(file.getFile());
//                .read(ResourceUtils.getFile("file:/static/img/diplom/" + getFileNameForSize(diplomSize)));
        addUserNameToDiplomImage(username, diplomImage, diplomSize);
        attachDiplomToResponse(response, diplomImage);
    }

    private static void addUserNameToDiplomImage(
            final String username,
            final BufferedImage diplomImage,
            final DiplomSize diplomSize) {

        final Graphics graphics = diplomImage.getGraphics();
        // color of the username
        graphics.setColor(Color.BLACK);
        // font and size of the username
        graphics.setFont(new Font("Arial black", Font.PLAIN, 60));
        setUserNameToPossition(username, graphics, diplomSize);
    }

    private static void setUserNameToPossition(
            final String username,
            final Graphics graphics,
            final DiplomSize diplomSize) {
        // middle position of the username 1200px: [600, 950], 800px: [400, 650]
        // middle of the image minus half of the username chars multiplied by 40 (if font is 50)
        final int xPosition = getXCoordinateMiddle(diplomSize) - username.length() / 2 * 43;
        final int yPosition = getYCoordinateMiddle(diplomSize);
        graphics.drawString(username.toUpperCase(), xPosition, yPosition); // y 650 is okay
    }

    private static String getFileNameForSize(final DiplomSize diplomSize) {
        return switch (diplomSize) {
            case SMALL -> "diplom_FIN_800px.jpg";
            case BIG -> "diplom_FIN_1200px.jpg";
        };
    }

    private static int getYCoordinateMiddle(final DiplomSize diplomSize) {
        return switch (diplomSize) {
            case BIG -> 975;
            case SMALL -> 650;
        };
    }

    private static int getXCoordinateMiddle(final DiplomSize diplomSize) {
        return switch (diplomSize) {
            case BIG -> 600;
            case SMALL -> 400;
        };
    }

    private static void attachDiplomToResponse(
            final HttpServletResponse response, final BufferedImage bufferedImage)
            throws IOException {

        final var out = response.getOutputStream();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        out.write(baos.toByteArray());
        out.close();
    }

    private static void prepareResponseHeaders(final String username, final HttpServletResponse response) {
        response.setContentType("application/jpg");
        final String headerKey = "Content-Disposition";
        final String headerValue = "attachment; filename=diplom_PoP2023_" + username + ".jpg";
        response.setHeader(headerKey, headerValue);
    }
}
