package io.github.craftedcart.mcliquidui.util;

import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

/**
 * Created by CraftedCart on 03/01/2016 (DD/MM/YYYY)
 */
public class DependencyUtils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void downloadFileWithWindow(String url, File fileToDownloadTo) throws IOException {
        JFrame frame = new JFrame("downloadFrame");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setTitle(StatCollector.translateToLocal("gui.mcliquidui:downloadingDeps"));

        JPanel panel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(24, 24, 24, 24);
        panel.setBorder(padding);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setContentPane(panel);

        JLabel titleLabel = new JLabel(StatCollector.translateToLocal("gui.mcliquidui:downloadingDeps"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        frame.getContentPane().add(titleLabel);

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 32)));

        JLabel urlLabel = new JLabel(url, SwingConstants.CENTER);
        frame.getContentPane().add(urlLabel);

        JLabel pathLabel = new JLabel(fileToDownloadTo.getAbsolutePath(), SwingConstants.CENTER);
        frame.getContentPane().add(pathLabel);

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 32)));

        JLabel dlProgLabel = new JLabel("0 / ? b (000.00%)", SwingConstants.CENTER);
        frame.getContentPane().add(dlProgLabel);

        JProgressBar dlProgBar = new JProgressBar(SwingConstants.CENTER);
        frame.getContentPane().add(dlProgBar);

        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
        frame.toFront();

        //Download it
        try {

            fileToDownloadTo.getParentFile().mkdirs();
            File tempFile = new File(fileToDownloadTo.getParentFile(), "downloadInProgress");

            if (Files.exists(tempFile.toPath())) {
                tempFile.delete();
            }

            tempFile.createNewFile();

            URL website = new URL(url);
            URLConnection urlConnection = website.openConnection();
            InputStream is = urlConnection.getInputStream();
            FileOutputStream fos = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            long fileSize = urlConnection.getContentLength();
            dlProgBar.setMaximum((int) fileSize);

            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                dlProgLabel.setText(String.format("%d / %d b (%06.2f%%)", totalBytesRead, fileSize, (float) totalBytesRead / fileSize * 100));
                dlProgBar.setValue((int) totalBytesRead);
            }

            fos.close();
            Files.move(tempFile.toPath(), fileToDownloadTo.toPath());

        } catch (IOException e) {

            LogHelper.info(String.format("Failed to download %s because of %s", url, ExceptionUtils.getMessage(e)));

            final JDialog failDialog = new JDialog(frame);
            failDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            failDialog.setTitle(StatCollector.translateToLocal("gui.mcliquidui:downloadingDeps"));
            failDialog.setModal(true);

            JPanel failPanel = new JPanel();
            failPanel.setBorder(padding);
            failPanel.setLayout(new BoxLayout(failPanel, BoxLayout.Y_AXIS));
            failDialog.setContentPane(failPanel);

            JLabel failTitleLabel = new JLabel(StatCollector.translateToLocal("gui.mcliquidui:downloadFailed"), SwingConstants.CENTER);
            failTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
            failDialog.getContentPane().add(failTitleLabel);

            failDialog.getContentPane().add(Box.createRigidArea(new Dimension(0, 32)));

            JLabel failInfoLabel = new JLabel(StatCollector.translateToLocal("gui.mcliquidui:loadCannotContinue"), SwingConstants.CENTER);
            failDialog.getContentPane().add(failInfoLabel);

            failDialog.getContentPane().add(Box.createRigidArea(new Dimension(0, 32)));

            JLabel errorLabel = new JLabel(ExceptionUtils.getMessage(e));
            failDialog.getContentPane().add(errorLabel);

            failDialog.getContentPane().add(Box.createRigidArea(new Dimension(0, 32)));

            final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                JButton manualInstallButton = new JButton(StatCollector.translateToLocal("gui.mcliquidui:manualInstall"));
                failDialog.getContentPane().add(manualInstallButton);

                manualInstallButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            desktop.browse(URI.create("http://craftedcart.github.io/Modular-FluxFields/documentation/manualDepInstall.html"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }

            JButton quitButton = new JButton(StatCollector.translateToLocal("gui.mcliquidui:quit"));
            failDialog.getContentPane().add(quitButton);
            quitButton.getRootPane().setDefaultButton(quitButton);

            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    failDialog.dispose();
                }
            });

            failDialog.pack();
            failDialog.setLocation(dim.width / 2 - failDialog.getSize().width / 2, dim.height / 2 - failDialog.getSize().height / 2);
            failDialog.setVisible(true);
            failDialog.toFront();

            throw new IOException(e);

        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }

    public static String httpGetString(String url) throws IOException {

        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();

    }

    public static BufferedImage httpGetImage(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

}
