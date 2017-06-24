///*
// * SpringBoot-AirportDemoProject  Copyright (C) 2017  Michael Haddon
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Affero General Public License version 3
// * as published by the Free Software Foundation.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU Affero General Public License for more details.
// *
// * You should have received a copy of the GNU Affero General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package com.airportdemo.modules;
//
//import org.hibernate.search.annotations.*;
//
//@Fields({
//        @Field(name = "name", index = Index.YES, store = Store.YES,
//                analyze = Analyze.YES, analyzer = @Analyzer(definition = SearchAnalysers.ENGLISH_WORD_ANALYSER), boost = @Boost(2.0f)),
//        @Field(name = "name.edge", index = Index.YES, store = Store.NO,
//                analyze = Analyze.YES, analyzer = @Analyzer(definition = SearchAnalysers.EDGE_ANALYSER)),
//        @Field(name = "name.ngram", index = Index.YES, store = Store.NO,
//                analyze = Analyze.YES, analyzer = @Analyzer(definition = SearchAnalysers.NGRAM_ANALYSER))
//})
//public @interface Search {
//}
