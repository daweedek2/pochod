package cz.kostka.pochod.service;

import cz.kostka.pochod.enums.DiplomSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class DiplomCreator {
    private static final Logger log = LoggerFactory.getLogger(DiplomCreator.class);

    public void download(
            final String username,
            final HttpServletResponse response,
            final DiplomSize diplomSize)
            throws IOException {

        if (response == null) {
            log.error("Diplom cannot be created because response is NULL!");
            return;
        }

        prepareResponseHeaders(username, response);
        BufferedImage diplomImage = ImageIO.read(
                new ClassPathResource("/static/img/diplom/" + getFileNameForSize(diplomSize)).getInputStream());
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
        graphics.setFont(new Font("Arial black", Font.BOLD, 60));
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
