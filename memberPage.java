import java.awt.BorderLayout; // นำเข้า BorderLayout สำหรับจัดการเลย์เอาต์ของคอมโพเนนต์
import java.awt.Color; // นำเข้า Color สำหรับจัดการสี
import java.awt.Font; // นำเข้า Font สำหรับจัดการฟอนต์
import java.awt.Graphics; // นำเข้า Graphics สำหรับการวาดรูป
import java.awt.Image; // นำเข้า Image สำหรับจัดการรูปภาพ
import java.awt.event.ActionEvent; // นำเข้า ActionEvent สำหรับการจัดการเหตุการณ์
import java.awt.event.ActionListener; // นำเข้า ActionListener สำหรับการฟังเหตุการณ์
import javax.swing.ImageIcon; // นำเข้า ImageIcon สำหรับการใช้รูปภาพใน Swing
import javax.swing.JButton; // นำเข้า JButton สำหรับปุ่ม
import javax.swing.JFrame; // นำเข้า JFrame สำหรับสร้างหน้าต่าง
import javax.swing.JLabel; // นำเข้า JLabel สำหรับข้อความและรูปภาพ
import javax.swing.JPanel; // นำเข้า JPanel สำหรับจัดกลุ่มคอมโพเนนต์
import javax.swing.Timer; // นำเข้า Timer สำหรับการทำงานตามเวลาที่กำหนด;
import java.awt.Dimension;

public class memberPage {
    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame(); // สร้างอ็อบเจ็กต์ของ MyFrame
        myFrame.setVisible(true); // แสดงหน้าต่าง
    }
}

class MyFrame extends JFrame {
    private Image originalImage; // ตัวแปรเก็บรูปภาพพื้นหลัง
    private JLabel[] imageLabels; // อาเรย์ของ JLabel สำหรับแสดงรูปภาพ
    private Image[] originalImages; // อาเรย์ของ Image สำหรับเก็บรูปภาพดั้งเดิม
    private int[][] bounds; // อาเรย์ 2 มิติสำหรับเก็บตำแหน่งและขนาดดั้งเดิมของรูปภาพ
    private final Timer movementTimer; // Timer สำหรับการขยายและหดตัวของรูปภาพ
    private int scaleStep = 40; // ขั้นตอนการขยายและหดตัวของรูปภาพ
    private final int scaleSpeed = 300; // ความเร็วของ Timer
    private boolean isShrinking = true; // สถานะของการขยายหรือหดตัว

    public MyFrame() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ตั้งค่า JFrame ให้เต็มหน้าจอ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ปิดโปรแกรมเมื่อปิดหน้าต่าง
        setTitle("Member"); // ตั้งชื่อหน้าต่าง

        originalImage = new ImageIcon("temP3.jpg").getImage(); // โหลดรูปภาพพื้นหลัง
        ImagePanel backgroundPanel = new ImagePanel(originalImage); // สร้าง ImagePanel ด้วยรูปภาพพื้นหลัง

        String[] image = { "fiw.png", "po.png", "tangkwa.png" }; // รายชื่อไฟล์รูปภาพ
        imageLabels = new JLabel[image.length]; // สร้างอาเรย์ JLabel สำหรับแสดงรูปภาพ
        originalImages = new Image[image.length]; // สร้างอาเรย์ Image สำหรับเก็บรูปภาพดั้งเดิม
        bounds = new int[image.length][4]; // สร้างอาเรย์ 2 มิติสำหรับเก็บตำแหน่งและขนาด
        int[][] size = { { 300, 510 }, { 240, 510 }, { 360, 510 } }; // ขนาดของรูปภาพ
        int[][] location = { { 155, 100 }, { 625, 100 }, { 1035, 100 } }; // ตำแหน่งของรูปภาพ
        String[] captions = { "Sathima kanlayasai 66011212141", "Wanuda Yeesarapat 66011212129",
                "Tullaya Duangmala 66011212021" }; // ข้อความที่จะใช้แสดงใต้รูปภาพ

        int maxWidth = 0; // ใช้หาความกว้างสูงสุด
        for (int i = 0; i < size.length; i++) {
            if (size[i][0] > maxWidth) {
                maxWidth = size[i][0]; // หาค่าความกว้างสูงสุด
            }
        }

        for (int i = 0; i < image.length; i++) {
            try {
                ImageIcon icon = new ImageIcon(image[i]); // สร้าง ImageIcon จากไฟล์รูปภาพ
                if (icon.getImage() == null) {
                    throw new RuntimeException("Cannot load image: " + image[i]); // ถ้าไม่สามารถโหลดรูปภาพได้ ให้โยน
                                                                                  // RuntimeException
                }
                originalImages[i] = icon.getImage(); // เก็บรูปภาพดั้งเดิม
                Image adjustImage = originalImages[i].getScaledInstance(size[i][0], size[i][1], Image.SCALE_SMOOTH); // ปรับขนาดรูปภาพ
                imageLabels[i] = createLabel(adjustImage, size[i][0], size[i][1]); // สร้าง JLabel สำหรับแสดงรูปภาพ
                imageLabels[i].setBounds(location[i][0], location[i][1], size[i][0], size[i][1]); // ตั้งค่าตำแหน่งและขนาดของ
                                                                                                  // JLabel
                backgroundPanel.add(imageLabels[i]); // เพิ่ม JLabel ลงใน ImagePanel

                bounds[i] = new int[] { location[i][0], location[i][1], size[i][0], size[i][1] }; // เก็บตำแหน่งและขนาดของรูปภาพ

                JPanel panel = new JPanel(); // สร้าง JPanel สำหรับแสดงข้อความ
                panel.setBounds(location[i][0], location[i][1] + size[i][1], maxWidth, 60); // ตั้งค่าตำแหน่งและขนาดของ
                                                                                            // JPanel
                panel.setBackground(new Color(200, 200, 200, 150)); // ตั้งสีพื้นหลังของ JPanel
                panel.setLayout(null); // ตั้งค่า Layout ของ JPanel เป็น null

                JLabel infoLabel = new JLabel(captions[i]); // สร้าง JLabel สำหรับข้อความ
                infoLabel.setForeground(Color.BLACK); // ตั้งสีข้อความเป็นสีดำ
                infoLabel.setHorizontalAlignment(JLabel.CENTER); // จัดข้อความให้กึ่งกลางแนวนอน
                infoLabel.setVerticalAlignment(JLabel.CENTER); // จัดข้อความให้กึ่งกลางแนวตั้ง

                Font customFont = new Font("MV Boli", Font.BOLD, 20); // กำหนดฟอนต์ของข้อความ
                infoLabel.setFont(customFont); // ตั้งค่าฟอนต์ให้กับ JLabel

                infoLabel.setBounds(0, 0, maxWidth, panel.getHeight()); // ตั้งค่าตำแหน่งและขนาดของข้อความ

                panel.add(infoLabel); // เพิ่ม JLabel ข้อความลงใน JPanel
                backgroundPanel.add(panel); // เพิ่ม JPanel ลงใน ImagePanel

            } catch (Exception e) {
                e.printStackTrace(); // แสดงข้อผิดพลาดถ้ามี
            }
        }

        JButton button = new JButton("Back"); // สร้างปุ่ม "Back"
        button.setBounds(1350, 720, 150, 50); // ตั้งค่าตำแหน่งและขนาดของปุ่ม
        button.setBackground(Color.PINK); // ตั้งสีพื้นหลังของปุ่ม
        button.setForeground(Color.BLACK); // ตั้งสีข้อความของปุ่ม
        button.setFont(new Font("Monospaced", Font.BOLD, 25)); // ตั้งฟอนต์ของปุ่ม
        button.addActionListener(new ActionListener() { // เพิ่ม ActionListener ให้กับปุ่ม
            @Override
            public void actionPerformed(ActionEvent e) { // เมื่อปุ่มถูกกด
                System.exit(0); // ปิดโปรแกรม
            }
        });
        backgroundPanel.add(button); // เพิ่มปุ่มลงใน ImagePanel

        setLayout(new BorderLayout()); // ตั้งค่า Layout ของ JFrame เป็น BorderLayout
        add(backgroundPanel, BorderLayout.CENTER); // เพิ่ม ImagePanel ลงใน JFrame
        revalidate(); // ทำการตรวจสอบและจัดการ Layout ใหม่
        repaint(); // วาดหน้าต่างใหม่

        movementTimer = new Timer(scaleSpeed, new ActionListener() { // สร้าง Timer สำหรับการเคลื่อนไหว
            @Override
            public void actionPerformed(ActionEvent e) { // เมื่อ Timer ทำงาน
                for (int i = 0; i < imageLabels.length; i++) { // วนลูปผ่านป้ายแสดงรูปภาพทั้งหมด
                    resizeImage(imageLabels[i], originalImages[i], bounds[i]); // ปรับขนาดของรูปภาพ
                }
            }
        });
        movementTimer.start(); // เริ่มต้น Timer
    }

    private JLabel createLabel(Image image, int width, int height) {
        ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH)); // สร้าง ImageIcon
                                                                                                    // จากรูปภาพที่ปรับขนาดแล้ว
        JLabel label = new JLabel(icon); // สร้าง JLabel และกำหนดไอคอนเป็น ImageIcon ที่สร้างขึ้น
        label.setSize(width, height); // ตั้งค่าขนาดของ JLabel
        return label; // ส่งคืน JLabel ที่สร้างขึ้น
    }

    private void resizeImage(JLabel label, Image originalImage, int[] originalBounds) {
        int width = label.getWidth(); // รับความกว้างปัจจุบันของ JLabel
        int height = label.getHeight(); // รับความสูงปัจจุบันของ JLabel

        if (isShrinking) { // ถ้ากำลังหด
            width -= scaleStep; // ลดความกว้างลง
            height -= scaleStep; // ลดความสูงลง
            if (width <= originalBounds[2] || height <= originalBounds[3]) { // ถ้าความกว้างหรือความสูงเล็กกว่าหรือเท่ากับขนาดดั้งเดิม
                isShrinking = false; // เปลี่ยนสถานะเป็นขยาย
            }
        } else { // ถ้ากำลังขยาย
            width += scaleStep; // เพิ่มความกว้าง
            height += scaleStep; // เพิ่มความสูง
            if (width >= originalBounds[2] || height >= originalBounds[3]) { // ถ้าความกว้างหรือความสูงใหญ่กว่าหรือเท่ากับขนาดดั้งเดิม
                isShrinking = true; // เปลี่ยนสถานะเป็นหด
            }
        }

        width = Math.max(width, 50); // ตั้งค่าความกว้างขั้นต่ำที่ 50
        height = Math.max(height, 50); // ตั้งค่าความสูงขั้นต่ำที่ 50

        Image adjustImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // สร้างรูปภาพที่ปรับขนาดแล้ว
        label.setIcon(new ImageIcon(adjustImage)); // ตั้งค่าไอคอนของ JLabel เป็นรูปภาพที่ปรับขนาดแล้ว
        label.setSize(width, height); // ตั้งค่าขนาดของ JLabel

        int x = originalBounds[0] + (originalBounds[2] - width) / 2; // คำนวณตำแหน่ง x ใหม่เพื่อจัดให้อยู่กึ่งกลาง
        int y = originalBounds[1] + (originalBounds[3] - height) / 2; // คำนวณตำแหน่ง y ใหม่เพื่อจัดให้อยู่กึ่งกลาง
        label.setLocation(x, y); // ตั้งค่าตำแหน่งของ JLabel
    }
}

class ImagePanel extends JPanel {
    private Image image; // ตัวแปรเก็บรูปภาพ

    public ImagePanel(Image image) {
        this.image = image; // กำหนดรูปภาพให้กับ ImagePanel
        setLayout(null); // ตั้งค่า Layout ของ JPanel เป็น null เพื่อจัดการตำแหน่งเอง
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // เรียกใช้ paintComponent ของ super class
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // วาดรูปภาพลงบน JPanel โดยปรับขนาดให้เต็มพื้นที่ของ
                                                                 // JPanel
    }
}
