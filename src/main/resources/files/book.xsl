<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="data">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-width="210mm" page-height="297mm">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="16pt" font-weight="bold" text-align="center" margin-bottom="10mm">
                        Book List
                    </fo:block>

                    <fo:table width="100%" table-layout="fixed" border-collapse="collapse">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>
                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell background-color="#CCCCCC" text-align="center" padding="2mm"
                                               border="1pt solid black">
                                    <fo:block font-weight="bold">Title</fo:block>
                                </fo:table-cell>
                                <fo:table-cell background-color="#CCCCCC" text-align="center" padding="2mm"
                                               border="1pt solid black">
                                    <fo:block font-weight="bold">Author</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>
                        <fo:table-body>
                            <xsl:apply-templates select="item"/>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="item">
        <fo:table-row>
            <fo:table-cell border="1pt solid black" padding="2mm">
                <fo:block>
                    <xsl:value-of select="title"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border="1pt solid black" padding="2mm">
                <fo:block>
                    <xsl:value-of select="author"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

</xsl:stylesheet>
