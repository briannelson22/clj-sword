# clj-sword

A clojure API for performing search and analysis of Biblical texts. Data is stored in the common OSIS format.

## Installation

Install with Git. 

Use with your favorite Clojure development tools.

## Usage


## Options


## Examples


### Core Concepts

Most Bible study software searches for text strings within chapters of the Bible. This works fine for many situations. But it seems like we're stuck in a rut.

I wanted to apply modern data analysis tools to the Bible and see what is feasible.

1. The basic unit of division is the word.
2. Words are grouped together into books(not chapters).
3. Any word(or sequence of words) can be tagged with meta data
   - Chapter
   - Verse
   - Author
   - Audience
   - Strongs Numbers
   - References to other words(groups of words)
   - Notes
   - Classifications
   - Topic/Sermon/any grouping
4. Searches operate on any word/metadata. 
   Examples:
    
	(search (word "god"))		// Searches for the word "god"
	(search (proximity 		// Search for the word "god" within 10 words of any word with :creation meta data
		  10 
		  (word "god") 
		  (tag :creation)))

Other ideas
- Tagging clouds like what you see in internet trending. Show the strength of the relationship between words/concepts. 
- Present this as a google like interface with meta data overlays

## License

Copyright © 2014 Brian Nelson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
