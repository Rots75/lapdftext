package edu.isi.bmkeg.pdf.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.isi.bmkeg.pdf.model.ChunkBlock;
import edu.isi.bmkeg.pdf.model.Document;
import edu.isi.bmkeg.pdf.model.PageBlock;
import edu.isi.bmkeg.pdf.model.ordering.SpatialOrdering;

public class SpatiallyOrderedChunkTextWriter implements TextWriter
{
	private StringBuilder text;
	public SpatiallyOrderedChunkTextWriter()
	{
	}

	@Override
	public void write(Document document, String outputFilename) throws IOException,FileNotFoundException
	{
		text = new StringBuilder();
		int totalNumberOfPages = document.getTotalNumberOfPages();
		PageBlock page;
		for (int i = 1; i <= totalNumberOfPages; i++)
		{
			page = document.getPage(i);
			List<ChunkBlock> chunksPerPage = page.getAllChunkBlocks(SpatialOrdering.PAGE_COLUMN_AWARE_MIXED_MODE);
			for(ChunkBlock chunkBlock:chunksPerPage){
				if(!chunkBlock.getType().equals(ChunkBlock.TYPE_FOOTER)&&!chunkBlock.getType().equals(ChunkBlock.TYPE_HEADER)){
					text.append(chunkBlock.getchunkText() + "\n");
				}
			}
		}
		ReadWriteTextFileWithEncoding.write(outputFilename, TextWriter.UTF_8, text.toString());

	}




}
