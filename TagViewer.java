import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;

import static java.nio.file.StandardOpenOption.CREATE;

public class TagViewer extends JFrame{
    JPanel mainPnl;
    JPanel buttonPnl;
    JPanel textPnl;
    JButton quitBtn;
    JButton openFileBtn;
    JButton extractTagsBtn;
    JTextArea textArea;
    JScrollPane scroller;
    public TagViewer() {
        setTitle("Tic Tac Toe");
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        createTextArea();
        mainPnl.add(textPnl, BorderLayout.NORTH);
        createButtonPanel();
        mainPnl.add(buttonPnl,BorderLayout.SOUTH);
        add(mainPnl);
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void createTextArea(){
        textPnl = new JPanel();
        textPnl.setLayout(new BorderLayout());
        textArea = new JTextArea("",15,30);
        scroller = new JScrollPane(textArea);
        textPnl.add(scroller);

    }
    private void createButtonPanel() {
        buttonPnl = new JPanel();
        buttonPnl.setLayout(new GridLayout(1, 3));
        openFileBtn = new JButton("Open File");
        extractTagsBtn = new JButton("Extract Tags");
        quitBtn = new JButton("Quit");
        openFileBtn.addActionListener((ActionEvent ae) ->{
            textArea.setText("");
            StopWordFilter filter = new StopWordFilter();
            JFileChooser chooser = new JFileChooser();
            String rec = "";
            ArrayList<String> lines = new ArrayList();

            try {
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);
                if (chooser.showOpenDialog((Component)null) == 0) {
                    File selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();
                    InputStream in = new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.CREATE));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    for(int line = 0; reader.ready(); ++line) {
                        rec = reader.readLine();
                        lines.add(rec);
                    }

                    reader.close();
                    System.out.println("\n\nData file read!");
                    Iterator var17 = lines.iterator();

                    while(var17.hasNext()) {
                        String l = (String)var17.next();
                        String[] fields = l.split(" ");
                        String[] var19 = fields;
                        int var20 = fields.length;

                        for(int var21 = 0; var21 < var20; ++var21) {
                            String w = var19[var21];
                            if (filter.accept(w)) {
                                textArea.append(w+": 1 \n");
                            }
                        }
                    }
                } else {
                    System.out.println("Failed to choose a file to process");
                    System.out.println("Run the program again!");
                    System.exit(0);
                }
            } catch (FileNotFoundException var23) {
                System.out.println("File not found!!!");
                var23.printStackTrace();
            } catch (IOException var24) {
                var24.printStackTrace();
            }

    });
        extractTagsBtn.addActionListener((ActionEvent ae) -> {
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.txt");

            try
            {
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                OutputStream out =
                        new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(out));

                // Finally can write the file LOL!

                writer.write(textArea.getText());
                writer.close(); // must close the file to seal it and flush buffer
                System.out.println("Data file written!");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        });

            quitBtn.addActionListener((ActionEvent ae) ->
        {
            System.exit(0);
        });
        buttonPnl.add(openFileBtn);
        buttonPnl.add(extractTagsBtn);
        buttonPnl.add(quitBtn);

    }
}
