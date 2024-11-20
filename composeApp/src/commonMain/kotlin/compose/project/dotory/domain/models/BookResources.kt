package compose.project.dotory.domain.models

import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.book_classic1
import dotory.composeapp.generated.resources.book_classic2
import dotory.composeapp.generated.resources.book_classic3
import dotory.composeapp.generated.resources.book_classic4
import dotory.composeapp.generated.resources.book_family1
import dotory.composeapp.generated.resources.book_family2
import dotory.composeapp.generated.resources.book_family3
import dotory.composeapp.generated.resources.book_family4
import dotory.composeapp.generated.resources.book_fantasy1
import dotory.composeapp.generated.resources.book_fantasy2
import dotory.composeapp.generated.resources.book_fantasy3
import dotory.composeapp.generated.resources.book_fantasy4
import dotory.composeapp.generated.resources.bookshelf
import dotory.composeapp.generated.resources.bookside
import dotory.composeapp.generated.resources.boy_icon

object BookResources {
    private val fantasyBooks = listOf(
        Book(Res.drawable.book_fantasy3, "숲지키미 한경"),
        Book(Res.drawable.book_fantasy2, "친환경 방랑자"),
        Book(Res.drawable.book_fantasy1, "재활용 지팡이"),
        Book(Res.drawable.book_fantasy4, "모여봐요 쓰레기숲")
    )

    private val classicBooks = listOf(
        Book(Res.drawable.book_classic1, "재활용 왕국"),
        Book(Res.drawable.book_classic2, "조선시대 속 분리수거"),
        Book(Res.drawable.book_classic3, "쓰레기주면 안잡아먹지"),
        Book(Res.drawable.book_classic4, "더러워진 우물")
    )

    private val familyBooks = listOf(
        Book(Res.drawable.book_family1, "숲 속 청소"),
        Book(Res.drawable.book_family2, "친환경 캠핑"),
        Book(Res.drawable.book_family3, "가족 환경교육"),
        Book(Res.drawable.book_family4, "가족이 최고야")
    )

    val bookGenres = listOf(
        BookGenre("장르 : 판타지", fantasyBooks),
        BookGenre("장르 : 고전", classicBooks),
        BookGenre("장르 : 가족", familyBooks)
    )

    val bookshelfImage = Res.drawable.bookshelf
    val bookSideImage = Res.drawable.bookside
    val profileImage = Res.drawable.boy_icon
}