package trading.client

enum Page:
  case Catalogue, Trading

  def toNavLabel: String =
    this match
      case Catalogue => "Catalogue"
      case Trading => "Trading"



  def toUrlPath: String =
    this match
      case Catalogue => "/product"
      case Trading => "/trading"
