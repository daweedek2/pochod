package cz.kostka.pochod.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class DiplomService {

    public void download(
            final String username, final HttpServletResponse response)
            throws IOException {

        prepareResponseHeaders(username, response);
        BufferedImage diplomImage = ImageIO
                .read(ResourceUtils.getFile("classpath:static/img/srdce-large.jpg"));
        addUserNameToDiplomImage(username, diplomImage);
        attachDiplomToResponse(response, diplomImage);
    }

    private static void addUserNameToDiplomImage(final String username, final BufferedImage diplomImage) {
        final Graphics graphics = diplomImage.getGraphics();
        // color of the username
        graphics.setColor(Color.BLACK);
        // font and size of the username
        graphics.setFont(new Font("Arial black", Font.BOLD, 50));
        // position of the username
        graphics.drawString(username.toUpperCase(), 100, 250);
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
