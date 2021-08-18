# Conversion-Work
Conversion work in Java eg DOC to PDF , Image list to PDF.


# Convert Doc File TO Pdf
ConversionController -> docToPdf
Dependency : build.gradle
implementation group: 'org.bouncycastle', name: 'bcprov-jdk15on', version: '1.66'
implementation group: 'fr.opensagres.xdocreport', name: 'fr.opensagres.poi.xwpf.converter.pdf', version: '2.0.2'



# List Of Images To Be Added to A Single Pdf

// Can be changed according to requirement for image alignment for all
PDRectangle pageSize = new PDRectangle(0, 0, 595, 500);

ConversionController -> imagesToPdf
Dependency : build.gradle
implementation group: 'org.apache.pdfbox', name: 'pdfbox', version: '2.0.21'


